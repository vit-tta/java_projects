package ru.hse.bank.file.importer;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Класс для импорта csv файлов
 * @param <T> операция, категория или счет
 */
@AllArgsConstructor
public class CsvImporter<T> extends DataImport<T> {
    private final CsvMapper mapper;
    private final Class<T> elementType;

    @Override
    protected List<T> parseData(String content) {
        try {
            CsvSchema schema = mapper.schemaFor(elementType).withHeader();

            MappingIterator<T> it = mapper
                    .readerFor(elementType)
                    .with(schema)
                    .readValues(content);
            return it.readAll();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
