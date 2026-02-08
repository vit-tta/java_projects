package org.example.service.repository;

import org.example.model.thing.IInventory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
/**
 * Класс для сбора всей информации о вещах
 */
@Repository
public class InMemoryInventoryRepository implements InventoryRepositoryy {
    private final List<IInventory> inventoryItems = new ArrayList<>();
    private int inventoryCounter = 0;
    @Override
    public void addItem(IInventory item) {
        item.setInventoryNumber(inventoryCounter);
        inventoryCounter++;
        inventoryItems.add(item);
    }
    @Override
    public List<IInventory> getAllItems() {
        return new ArrayList<>(inventoryItems);
    }
}