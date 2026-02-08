package org.example.service;

import org.example.entity.AnalysisReport;
import org.example.repository.AnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Бизнес-логика анализа
 */
@Service
public class AnalysisService {

    @Autowired
    private AnalysisRepository analysisRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Transactional
    public AnalysisReport analyzeFile(Long fileId, Integer taskId) {
        try {
            // 1. Получаем информацию о файле из File Storing Service
            String fileUrl = "http://file-storing:8081/api/files/" + fileId;
            ResponseEntity<Map> fileResponse = restTemplate.getForEntity(fileUrl, Map.class);

            if (!fileResponse.getStatusCode().is2xxSuccessful() || fileResponse.getBody() == null) {
                throw new RuntimeException("File not found: " + fileId);
            }

            Map<String, Object> fileData = fileResponse.getBody();
            String studentName = (String) fileData.get("studentName");
            String fileName = (String) fileData.get("fileName");
            String fileContent = (String) fileData.get("fileContent");
            String uploadDateStr = (String) fileData.get("uploadDate");
            LocalDateTime uploadDate = LocalDateTime.parse(uploadDateStr);

            // 2. Проверяем на плагиат (передаем studentName)
            boolean isPlagiarism = checkPlagiarism(fileContent, taskId, uploadDate, studentName);
            String plagiarismSource = isPlagiarism ?
                    "Found submission from different student with same content" : null;

            // 3. Создаем и сохраняем отчет
            AnalysisReport report = new AnalysisReport();
            report.setFileId(fileId);
            report.setTaskId(taskId);
            report.setStudentName(studentName);
            report.setFileName(fileName);
            report.setIsPlagiarism(isPlagiarism);
            report.setPlagiarismSource(plagiarismSource);

            return analysisRepository.save(report);

        } catch (Exception e) {
            throw new RuntimeException("Analysis failed: " + e.getMessage(), e);
        }
    }
    private boolean checkPlagiarism(String fileContent, Integer taskId,
                                    LocalDateTime currentUploadDate, String currentStudentName) {
        try {
            // Получаем все файлы по этому заданию
            String filesUrl = "http://file-storing:8081/api/files/task/" + taskId;
            ResponseEntity<List> filesResponse = restTemplate.getForEntity(filesUrl, List.class);

            if (!filesResponse.getStatusCode().is2xxSuccessful() || filesResponse.getBody() == null) {
                return false;
            }

            List<Map<String, Object>> files = filesResponse.getBody();

            for (Map<String, Object> file : files) {
                String otherContent = (String) file.get("fileContent");
                String otherStudentName = (String) file.get("studentName");
                String otherUploadDateStr = (String) file.get("uploadDate");
                LocalDateTime otherUploadDate = LocalDateTime.parse(otherUploadDateStr);

                // 1. Пропускаем файлы того же студента (свои работы - не плагиат)
                if (currentStudentName.equals(otherStudentName)) {
                    continue;
                }

                // 2. Если содержимое одинаковое
                if (fileContent != null && fileContent.equals(otherContent)) {
                    // 3.  работа считается плагиатом, если:
                    //    - Содержимое совпадает с чужой работой
                    //    - Текущая работа была загружена ПОЗЖЕ чужой

                    System.out.println("Found same content from different student!");
                    System.out.println("Current student: " + currentStudentName + ", date: " + currentUploadDate);
                    System.out.println("Other student: " + otherStudentName + ", date: " + otherUploadDate);

                    if (currentUploadDate.isAfter(otherUploadDate)) {
                        System.out.println("PLAGIARISM: Current work is LATER than original!");
                        return true;
                    } else {
                        System.out.println("NOT plagiarism: Current work is EARLIER or at same time");
                        // Если текущая работа была сдана раньше, это не плагиат
                    }
                }
            }

            return false;

        } catch (Exception e) {
            System.err.println("Plagiarism check error: " + e.getMessage());
            return false;
        }
    }

    public List<Map<String, Object>> getTaskReport(Integer taskId) {
        List<AnalysisReport> reports = analysisRepository.findByTaskId(taskId);

        // Конвертируем в Map
        return reports.stream()
                .map(report -> {
                    Map<String, Object> reportMap = new HashMap<>();
                    reportMap.put("fileName", report.getFileName());
                    reportMap.put("studentName", report.getStudentName());
                    reportMap.put("isPlagiarism", report.getIsPlagiarism());
                    return reportMap;
                })
                .collect(Collectors.toList());
    }

    public List<AnalysisReport> getReportsByTask(Integer taskId) {
        return analysisRepository.findByTaskId(taskId);
    }

    public List<AnalysisReport> getAllReports() {
        return analysisRepository.findAll();
    }
}