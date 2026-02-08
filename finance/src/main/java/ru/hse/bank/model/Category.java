package ru.hse.bank.model;

import lombok.*;

/**
 * Класс для категорий
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private  int id;
    private OperationType type;
    private String name;

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
