package org.example.controller;

import org.example.entity.AnalysisReport;
import org.example.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Предоставляет REST API для работы с анализом файлов:
 * POST /api/analysis/analyze/{fileId} - запуск анализа файла на плагиат
 * GET /api/analysis/reports/task/{taskId} - получение отчетов по заданию
 * GET /api/analysis/reports - получение всех отчетов
 * GET /api/analysis/health - проверка здоровья сервиса
 */
@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;

    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @PostMapping("/analyze/{fileId}")
    public ResponseEntity<?> analyzeFile(
            @PathVariable Long fileId,
            @RequestBody Map<String, Object> request) {

        try {
            Integer taskId = (Integer) request.get("taskId");
            AnalysisReport report = analysisService.analyzeFile(fileId, taskId);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Analysis completed successfully");
            response.put("reportId", report.getId());
            response.put("fileId", report.getFileId());
            response.put("taskId", report.getTaskId());
            response.put("isPlagiarism", report.getIsPlagiarism());
            response.put("plagiarismSource", report.getPlagiarismSource());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Analysis failed: " + e.getMessage());
        }
    }

    @GetMapping("/reports/task/{taskId}")
    public ResponseEntity<?> getTaskReport(@PathVariable Integer taskId) {
        try {
            List<Map<String, Object>> reports = analysisService.getTaskReport(taskId);

            Map<String, Object> response = new HashMap<>();
            response.put("taskId", taskId);
            response.put("reports", reports);
            response.put("totalFiles", reports.size());

            // Проверяем наличие плагиата
            boolean hasPlagiarism = reports.stream()
                    .anyMatch(report -> Boolean.TRUE.equals(report.get("isPlagiarism")));
            response.put("hasPlagiarism", hasPlagiarism);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/reports")
    public ResponseEntity<?> getAllReports() {
        List<AnalysisReport> reports = analysisService.getAllReports();

        Map<String, Object> response = new HashMap<>();
        response.put("totalReports", reports.size());
        response.put("reports", reports);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("File Analysis Service is UP");
    }
}