package ru.hse.bank.file.exporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
/**
 * Абстрактный Класс для экспорта файлов. Паттерн Шаблонный метод
 * @param <T> операция, категория или счет
 */
public abstract class DataExport<T> {
    public final void exportData(List<T> data, String filePath) throws IOException {
        validateOutputDirectory(filePath);
        String content = parseData(data);
        writeToFile(content, filePath);
    }

    protected void validateOutputDirectory(String filePath) throws IOException {
        Path file = Paths.get(filePath);
        Path parentDir = file.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectory(parentDir);
        }
        if (Files.exists(file) && !Files.isWritable(file)) {
            throw new IllegalArgumentException("Нет прав на запись в файл: " + filePath);
        }
    }

    protected void writeToFile(String data, String filePath) {
        Path path = Paths.get(filePath);
        try {
            Files.writeString(path, data);
        } catch (Exception e) {
            throw new RuntimeException("ошибка");
        }
    }

    protected abstract String parseData(List<T> data);
}
