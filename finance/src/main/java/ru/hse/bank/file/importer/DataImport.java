package ru.hse.bank.file.importer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
/**
 * Класс для импорта файлов
 * @param <T> операция, категория или счет
 */
public abstract class DataImport<T> {
    public final List<T> importData(String filePath) throws IOException {
        validateInputFile(filePath);
        String content = readFromFile(filePath);
        return parseData(content);
    }

    protected void validateInputFile(String filePath) throws IOException {
        Path file = Paths.get(filePath);

        if (!Files.exists(file)) {
            throw new IllegalArgumentException("Файл не существует: " + filePath);
        }

        if (!Files.isReadable(file)) {
            throw new IllegalArgumentException("Нет прав на чтение файла: " + filePath);
        }

        if (Files.size(file) == 0) {
            throw new IllegalArgumentException("Файл пустой: " + filePath);
        }
    }

    protected String readFromFile(String filePath) {
        try {
            return Files.readString(Paths.get(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract List<T> parseData(String content);
}