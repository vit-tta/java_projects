package ru.hse.bank.file.exporter;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Класс для экспорта csv файлов
 * @param <T> операция, категория или счет
 */
@AllArgsConstructor
public class CsvExporter<T> extends DataExport<T> {
    private final CsvMapper csvMapper;
    private final Class<T> type;

    @Override
    protected String parseData(List<T> data) {
        try {
            CsvSchema schema = csvMapper.schemaFor(type).withHeader();
            return csvMapper.writer(schema).writeValueAsString(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
