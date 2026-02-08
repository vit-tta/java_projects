package ru.hse.bank.service;

import ru.hse.bank.model.Category;
import ru.hse.bank.model.OperationType;
import ru.hse.bank.storage.category.CategoryStorage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Бизнес-логика работы с категориями
 */
@Service
public class CategoryService {
    private final CategoryStorage categoryStorage;

    public CategoryService(CategoryStorage categoryStorage) {
        this.categoryStorage = categoryStorage;
    }

    public Category createCategory(OperationType type, String name) {
        Category category = Category.builder()
                .type(type)
                .name(name)
                .build();
        return categoryStorage.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryStorage.findAll();
    }

    public void deleteCategory(int id) {
        categoryStorage.delete(id);
    }

    public Category updateCategory(int id, String name, OperationType type) {
        return categoryStorage.findById(id)
                .map(category -> {
                    if (name != null) {
                        category.setName(name);
                    }
                    if (type != null) {
                        category.setType(type);
                    }
                    return categoryStorage.save(category);}).orElseThrow(() -> new IllegalArgumentException("Категория не найдена"));
    }
}
