package ru.hse.bank.file;

import org.springframework.stereotype.Service;
import ru.hse.bank.file.exporter.DataExport;
import ru.hse.bank.file.importer.DataImport;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Класс, реализующий паттерн Strategy (динамический выбор алгоритма экспорта/импорта во время выполнения)
 * @param <T>
 */
@Service
public class FileManager<T> {
    private final Map<String, DataExport<T>> exporters;
    private final Map<String, DataImport<T>> importers;

    public FileManager(Map<String, DataExport<T>> exporters, Map<String, DataImport<T>> importers) {
        this.exporters = exporters;
        this.importers = importers;
    }

    public void exportData(String exporterType, String filePath, List<T> data) throws IOException {
        DataExport<T> exporter = exporters.get(exporterType);
        exporter.exportData(data, filePath);
    }

    public List<T> importData(String importerType, String filePath) throws IOException {
        DataImport<T> importer = importers.get(importerType);
        return importer.importData(filePath);
    }
}
