package ru.hse.bank.storage.operation;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.hse.bank.file.FileManager;
import ru.hse.bank.model.Operation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Repository
@AllArgsConstructor
public class FileOperationStorage extends InMemoryOperationStorage {
    private final String dataFile = "export/operation.csv";
    private final FileManager<Operation> fileManager;

    @PostConstruct
    public void init() {
        loadFromFile();
    }

    private void loadFromFile() {
        if (Files.exists(Paths.get(dataFile))) {
            try {
                List<Operation> operations = fileManager.importData("CsvImporter.Operation", dataFile);
                operations.forEach(super::save);
                super.nextId = operations.size() + 1;
            } catch (IOException e) {
                System.err.println("Ошибка загрузки данных: " + e.getMessage());
            }
        }
    }

    private void saveToFile() {
        try {
            List<Operation> operations = this.findAll();
            fileManager.exportData("CsvExporter.Operation", dataFile, operations);
        } catch (IOException e) {
            System.err.println("Ошибка сохранения данных: " + e.getMessage());
        }
    }

    @Override
    public Operation save(Operation operation) {
        super.save(operation);
        saveToFile();
        return operation;
    }

    @Override
    public void delete(int id) {
        super.delete(id);
        saveToFile();
    }
}
