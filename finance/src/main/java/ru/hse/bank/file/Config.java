package ru.hse.bank.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.context.annotation.Bean;
import ru.hse.bank.file.exporter.CsvExporter;
import ru.hse.bank.file.exporter.DataExport;
import ru.hse.bank.file.exporter.JsonExporter;
import ru.hse.bank.file.importer.CsvImporter;
import ru.hse.bank.file.importer.DataImport;
import ru.hse.bank.file.importer.JsonImporter;
import ru.hse.bank.model.BankAccount;
import ru.hse.bank.model.Category;
import ru.hse.bank.model.Operation;

/**
 * Конфигурационный компонент Spring, который настраивает и регистрирует бины для системы импорта и экспорта данных
 */
@Configuration
public class Config {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule()); // для поддержки LocalDateTime
    }
    @Bean(name = "JsonExporter.BankAccount")
    public DataExport<BankAccount> jsonBankAccountExporter(ObjectMapper objectMapper) {
        return new JsonExporter<>(objectMapper, BankAccount.class);
    }

    @Bean("JsonImporter.BankAccount")
    public DataImport<BankAccount> jsonBankAccountImporter(ObjectMapper objectMapper) {
        return new JsonImporter<>(objectMapper, BankAccount.class);
    }

    // JSON экспортеры и импортеры для Category
    @Bean(name = "JsonExporter.Category")
    public DataExport<Category> jsonCategoryExporter(ObjectMapper objectMapper) {
        return new JsonExporter<>(objectMapper, Category.class);
    }

    @Bean("JsonImporter.Category")
    public DataImport<Category> jsonCategoryImporter(ObjectMapper objectMapper) {
        return new JsonImporter<>(objectMapper, Category.class);
    }

    // JSON экспортеры и импортеры для Operation
    @Bean(name = "JsonExporter.Operation")
    public DataExport<Operation> jsonOperationExporter(ObjectMapper objectMapper) {
        return new JsonExporter<>(objectMapper, Operation.class);
    }

    @Bean("JsonImporter.Operation")
    public DataImport<Operation> jsonOperationImporter(ObjectMapper objectMapper) {
        return new JsonImporter<>(objectMapper, Operation.class);
    }

    @Bean
    public CsvMapper csvMapper() {
        return CsvMapper.builder()
                .findAndAddModules()
                .build();
    }

    @Bean(name = "CsvExporter.BankAccount")
    public DataExport<BankAccount> csvBankAccountExporter(CsvMapper csvMapper) {
        return new CsvExporter<>(csvMapper, BankAccount.class);
    }

    @Bean("CsvImporter.BankAccount")
    public DataImport<BankAccount> csvBankAccountImporter(CsvMapper csvMapper) {
        return new CsvImporter<>(csvMapper, BankAccount.class);
    }

    @Bean(name = "CsvExporter.Category")
    public DataExport<Category> csvCategoryExporter(CsvMapper csvMapper) {
        return new CsvExporter<>(csvMapper, Category.class);
    }

    @Bean("CsvImporter.Category")
    public DataImport<Category> csvCategoryImporter(CsvMapper csvMapper) {
        return new CsvImporter<>(csvMapper, Category.class);
    }


    @Bean(name = "CsvExporter.Operation")
    public DataExport<Operation> csvOperationExporter(CsvMapper csvMapper) {
        return new CsvExporter<>(csvMapper, Operation.class);
    }

    @Bean("CsvImporter.Operation")
    public DataImport<Operation> csvOperationImporter(CsvMapper csvMapper) {
        return new CsvImporter<>(csvMapper, Operation.class);
    }
}


