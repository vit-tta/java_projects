package ru.hse.bank.storage.bankAccount;

import ru.hse.bank.file.FileManager;
import ru.hse.bank.model.BankAccount;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Класс расширяющий интерфейс. Паттерн Proxy (добавление сохранение в файл без изменения кода) + Фасад
 */
@Repository
@AllArgsConstructor
public class FileBankAccountStorage extends InMemoryBankAccountStorage{
    private final String dataFile = "export/bankAccounts.csv";
    private final FileManager<BankAccount> fileManager;
    @PostConstruct
    public void init(){
        loadFromFile();
    }

    private void loadFromFile() {
        if (Files.exists(Paths.get(dataFile))) {
            try {
                List<BankAccount> accounts = fileManager.importData("CsvImporter.BankAccount",dataFile);
                accounts.forEach(super::save);
                super.nextId = accounts.size() + 1;
            } catch (IOException e) {
                System.err.println("Ошибка загрузки данных: " + e.getMessage());
            }
        }
    }

    private void saveToFile() {
        try {
            List<BankAccount> accounts = this.findAll();
            fileManager.exportData("CsvExporter.BankAccount", dataFile,accounts); // Паттерн Фасад
        } catch (IOException e) {
            System.err.println("Ошибка сохранения данных: " + e.getMessage());
        }
    }

    @Override
    public BankAccount save(BankAccount account) {
        super.save(account);
        saveToFile();
        return account;
    }


    @Override
    public void delete(int id) {
        super.delete(id);
        saveToFile();
    }
}
