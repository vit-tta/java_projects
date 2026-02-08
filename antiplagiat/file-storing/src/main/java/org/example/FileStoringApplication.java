package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Точка входа Main
 * Сканирует и регистрирует все Spring компоненты
 */
@SpringBootApplication
public class FileStoringApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileStoringApplication.class, args);
    }
}