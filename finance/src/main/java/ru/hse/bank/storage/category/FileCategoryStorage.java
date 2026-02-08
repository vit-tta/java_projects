package ru.hse.bank.storage.category;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.hse.bank.file.FileManager;
import ru.hse.bank.model.Category;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Repository
@AllArgsConstructor
public class FileCategoryStorage extends InMemoryCategoryStorage {
    private final String dataFile = "export/categories.csv";
    private final FileManager<Category> fileManager;
    @PostConstruct
    public void init(){
        loadFromFile();
    }

    private void loadFromFile() {
        if (Files.exists(Paths.get(dataFile))) {
            try {
                List<Category> categories = fileManager.importData("CsvImporter.Category",dataFile);
                categories.forEach(super::save);
                super.nextId = categories.size() + 1;
            } catch (IOException e) {
                System.err.println("Ошибка загрузки данных: " + e.getMessage());
            }
        }
    }

    private void saveToFile() {
        try {
            List<Category> categories = this.findAll();
            fileManager.exportData("CsvExporter.Category", dataFile,categories);
        } catch (IOException e) {
            System.err.println("Ошибка сохранения данных: " + e.getMessage());
        }
    }

    @Override
    public Category save(Category category) {
        super.save(category);
        saveToFile();
        return category;
    }


    @Override
    public void delete(int id) {
        super.delete(id);
        saveToFile();
    }
}
