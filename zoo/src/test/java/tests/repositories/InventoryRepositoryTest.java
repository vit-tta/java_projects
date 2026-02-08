package tests.repositories;

import org.example.service.repository.InMemoryInventoryRepository;
import org.example.model.thing.IInventory;
import org.example.model.thing.type.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс, тестирующий добавление вещи и присваивание ей номера
 */
class InventoryRepositoryTest {

    private InMemoryInventoryRepository inventoryRepository;

    @BeforeEach
    void setUp() {
        inventoryRepository = new InMemoryInventoryRepository();
    }

    @Test
    void addItemShouldAssignInventoryNumberAndAddToList() {
        Table table = new Table(0);
        inventoryRepository.addItem(table);

        List<IInventory> items = inventoryRepository.getAllItems();
        assertEquals(1, items.size());
        assertEquals(0, table.getInventoryNumber());
        assertEquals(table, items.get(0));
    }
}
