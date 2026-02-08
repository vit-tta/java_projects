package ru.hse.bank.menu;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hse.bank.model.Category;
import ru.hse.bank.model.OperationType;
import ru.hse.bank.service.CategoryService;
import ru.hse.bank.service.input.InputSystem;

import java.util.List;

/**
 * Класс с меню, который отвечает за категории
 */
@Component
@AllArgsConstructor
public class CategoryMenu {
    private final CategoryService categoryService;
    private final InputSystem inputSystem;

    public void addCategory() {
        System.out.println("Вы Создаете новую категорию:");
        Category category = formCategory();
        try {
            categoryService.createCategory(category.getType(), category.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void showCategories() {
        System.out.println("Список категорий: ");
        List<Category> categories = categoryService.getAllCategories();
        for (Category category : categories) {
            System.out.println(category);
        }
    }

    public void changeCategory() {
        System.out.println("Вы Редактируете категорию:");
        System.out.println("Введите id категории: ");
        int id = inputSystem.getInt(0, Integer.MAX_VALUE);
        Category category = formCategory();
        try {
            categoryService.updateCategory(id, category.getName(), category.getType());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteCategory() {
        System.out.println("Введите id категории: ");
        int id = inputSystem.getInt(1, Integer.MAX_VALUE);
        try {
            categoryService.deleteCategory(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Category formCategory() {
        OperationType operationType = inputSystem.getChoice(List.of("Прибыль", "Трата")) == 1
                ? OperationType.INCOME : OperationType.EXPENSE;
        System.out.println("Введите название: ");
        String name = inputSystem.getString();
        Category category = Category.builder()
                .name(name)
                .type(operationType)
                .build();
        return category;
    }
}
