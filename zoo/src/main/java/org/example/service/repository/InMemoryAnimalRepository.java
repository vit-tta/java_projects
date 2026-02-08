package org.example.service.repository;

import org.example.model.animal.IAlive;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для сбора всей информации о животных
 */
@Repository
public class InMemoryAnimalRepository implements AnimalRepository{
    private final List<IAlive> animals = new ArrayList<>();
    private int inventoryCounter = 0;

    @Override
    public void addAnimal(IAlive animal) {
        animal.setInventoryNumber(inventoryCounter);
        inventoryCounter++;
        animals.add(animal);
    }

    @Override
    public List<IAlive> getAllAnimals() {
        return new ArrayList<>(animals);
    }

    public List<IAlive> getAnimalsForContactZoo() {
        return animals.stream()
                .filter(IAlive::canBeInContactZoo)
                .toList();
    }

    public int getTotalFoodConsumption() {
        return animals.stream()
                .mapToInt(IAlive::getAmountOfFood)
                .sum();
    }

    public int getAnimalCount() {
        return animals.size();
    }
}