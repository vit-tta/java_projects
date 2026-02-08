package org.example.controller;

import org.example.entity.StoredFile;
import org.example.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Предоставляет REST API для операций с файлами:
 * POST /api/files/upload - загрузка файла
 * GET /api/files/task/{taskId} - получение файлов по заданию
 * GET /api/files/{id} - получение файла по ID
 * GET /api/files/all - получение всех файлов
 * GET /api/files/health - проверка здоровья сервиса
 */
@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("taskId") Integer taskId,
            @RequestParam("file") MultipartFile file) {

        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }

            String studentName = firstName + " " + lastName;
            String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);

            // Просто сохраняем файл
            StoredFile savedFile = fileStorageService.saveFile(
                    studentName, taskId, file.getOriginalFilename(), fileContent
            );

            Map<String, Object> response = new HashMap<>();
            response.put("message", "File uploaded successfully");
            response.put("id", savedFile.getId());
            response.put("fileName", savedFile.getFileName());
            response.put("studentName", savedFile.getStudentName());
            response.put("taskId", savedFile.getTaskId());
            response.put("uploadDate", savedFile.getUploadDate());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/task/{taskId}")
    public List<StoredFile> getFilesByTask(@PathVariable Integer taskId) {
        return fileStorageService.getFilesByTask(taskId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFileById(@PathVariable Long id) {
        StoredFile file = fileStorageService.getFileById(id);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(file);
    }

    @GetMapping("/all")
    public List<StoredFile> getAllFiles() {
        return fileStorageService.getAllFiles();
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("File Storing Service is UP");
    }
}