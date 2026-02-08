package ru.hse.bank.file.importer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Класс для импорта json файлов
 * @param <T> операция, категория или счет
 */
@AllArgsConstructor
public class JsonImporter<T> extends DataImport<T> {
    private final ObjectMapper objectMapper;
    private final Class<T> type;

    @Override
    protected List<T> parseData(String content) {
        try {
            return objectMapper.readValue(content, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
