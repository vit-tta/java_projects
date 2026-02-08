package ru.hse.bank.file.exporter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Класс для экспорта json файлов
 * @param <T> операция, категория или счет
 */
@AllArgsConstructor
public class JsonExporter<T> extends DataExport<T> {
    private final ObjectMapper objectMapper;
    private final Class<T> type;

    @Override
    protected String parseData(List<T> data) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка форматирования JSON", e);
        }
    }
}
