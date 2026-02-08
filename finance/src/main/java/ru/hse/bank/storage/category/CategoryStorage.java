package ru.hse.bank.storage.category;

import ru.hse.bank.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryStorage {
    Category save(Category category);
    Optional<Category> findById(int id);
    List<Category> findAll();
    void delete(int id);
}
