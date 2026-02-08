package org.example.service;

import jakarta.transaction.Transactional;
import org.example.entity.StoredFile;
import org.example.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Бизнес-логика работы с файлами
 */
@Service
public class FileStorageService {
    @Autowired
    private FileRepository fileRepository;

    @Transactional
    public StoredFile saveFile(String studentName, Integer taskId,
                               String fileName, String fileContent) {

        StoredFile file = new StoredFile();
        file.setFileName(fileName);
        file.setStudentName(studentName);
        file.setTaskId(taskId);
        file.setFileContent(fileContent);
        file.setUploadDate(LocalDateTime.now());

        return fileRepository.save(file);
    }

    public List<StoredFile> getFilesByTask(Integer taskId) {
        return fileRepository.findByTaskId(taskId);
    }

    public List<StoredFile> getAllFiles() {
        return fileRepository.findAll();
    }

    public StoredFile getFileById(Long id) {
        return fileRepository.findById(id).orElse(null);
    }

}