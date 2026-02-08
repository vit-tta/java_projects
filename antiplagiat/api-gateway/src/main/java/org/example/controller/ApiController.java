package org.example.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Контроллер API Gateway
 * Принимает запросы от клиентов (Postman),
 * Направляет запросы к соответствующим микросервисам,
 * Собирает ответы от нескольких микросервисов,
 * Обрабатывает ошибки и форматирует ответы
 */
@RestController
@RequestMapping("/api")
public class ApiController {
    private final RestTemplate restTemplate;

    public ApiController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/files")
    public ResponseEntity<?> saveFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("taskId") Integer taskId) {

        try {
            System.out.println("Received file upload request:");
            System.out.println("firstName: " + firstName);
            System.out.println("lastName: " + lastName);
            System.out.println("taskId: " + taskId);
            System.out.println("fileName: " + file.getOriginalFilename());
            System.out.println("fileSize: " + file.getSize());

            // 1. Сохраняем файл в File Storing Service
            String storingUrl = "http://file-storing:8081/api/files/upload";

            // Создаем MultiValueMap для multipart запроса
            // Все значения string
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("firstName", firstName);
            body.add("lastName", lastName);
            body.add("taskId", String.valueOf(taskId));

            body.add("file", new MultipartFileResource(file));

            // Устанавливаем заголовки
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            System.out.println("Sending request to File Storing Service...");
            HttpEntity<MultiValueMap<String, Object>> requestEntity =
                    new HttpEntity<>(body, headers);

            ResponseEntity<Map> storingResponse = restTemplate.postForEntity(
                    storingUrl, requestEntity, Map.class
            );

            System.out.println("File Storing Response: " + storingResponse.getStatusCode());

            if (storingResponse.getStatusCode() != HttpStatus.OK) {
                return ResponseEntity.status(storingResponse.getStatusCode())
                        .body(storingResponse.getBody());
            }

            Map<String, Object> storingResult = storingResponse.getBody();
            Long fileId = Long.valueOf(storingResult.get("id").toString());

            // 2. Запускаем анализ в File Analysis Service
            String analysisUrl = "http://file-analysis:8082/api/analysis/analyze/" + fileId;

            Map<String, Object> analysisRequest = new HashMap<>();
            analysisRequest.put("taskId", taskId);

            // Для анализа используем JSON
            HttpHeaders jsonHeaders = new HttpHeaders();
            jsonHeaders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> analysisEntity =
                    new HttpEntity<>(analysisRequest, jsonHeaders);

            System.out.println("Sending analysis request...");
            ResponseEntity<Map> analysisResponse = restTemplate.postForEntity(
                    analysisUrl, analysisEntity, Map.class
            );

            System.out.println("Analysis Response: " + analysisResponse.getStatusCode());

            // 3. Формируем общий ответ
            Map<String, Object> response = new HashMap<>();
            response.put("message", "File uploaded and analyzed successfully");
            response.put("fileId", fileId);
            response.put("storingResult", storingResult);
            response.put("analysisResult", analysisResponse.getBody());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Failed to upload file",
                            "message", e.getMessage(),
                            "details", e.toString()
                    ));
        }
    }

    // Вспомогательный класс для правильной передачи файла
    private static class MultipartFileResource extends ByteArrayResource {
        private final String filename;

        public MultipartFileResource(MultipartFile file) throws IOException {
            super(file.getBytes());
            this.filename = file.getOriginalFilename();
        }

        @Override
        public String getFilename() {
            return filename;
        }
    }

    @GetMapping("/files/{taskId}/report")
    public ResponseEntity<?> getReport(@PathVariable int taskId) {
        String url = "http://file-analysis:8082/api/analysis/reports/task/" + taskId;

        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<?> healthCheck() {
        Map<String, Object> healthStatus = new HashMap<>();
        healthStatus.put("apiGateway", "UP");

        try {
            ResponseEntity<String> storingResponse = restTemplate.getForEntity(
                    "http://file-storing:8081/api/files/health", String.class);
            healthStatus.put("fileStoring", storingResponse.getBody());
        } catch (Exception e) {
            healthStatus.put("fileStoring", "DOWN: " + e.getMessage());
        }

        try {
            ResponseEntity<String> analysisResponse = restTemplate.getForEntity(
                    "http://file-analysis:8082/api/analysis/health", String.class);
            healthStatus.put("fileAnalysis", analysisResponse.getBody());
        } catch (Exception e) {
            healthStatus.put("fileAnalysis", "DOWN: " + e.getMessage());
        }

        return ResponseEntity.ok(healthStatus);
    }
}