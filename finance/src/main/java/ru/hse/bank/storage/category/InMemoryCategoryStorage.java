package ru.hse.bank.storage.category;

import ru.hse.bank.model.Category;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryCategoryStorage implements CategoryStorage{

    private final Map<Integer, Category> categories = new LinkedHashMap<>();
    protected int nextId = 1;

    @Override
    public Category save(Category category) {
        if (category.getId() == 0) {
            category.setId(nextId++);
        }
        categories.put(category.getId(), category);
        return category;
    }

    @Override
    public Optional<Category> findById(int id) {
        return Optional.ofNullable(categories.get(id));
    }

    @Override
    public List<Category> findAll() {
        return List.copyOf(categories.values());
    }


    @Override
    public void delete(int id) {
        categories.remove(id);
    }
}
