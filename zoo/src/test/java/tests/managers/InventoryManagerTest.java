package tests.managers;

import org.example.service.manager.InventoryManager;
import org.example.service.repository.InMemoryInventoryRepository;
import org.example.model.thing.type.Computer;
import org.example.model.thing.IInventory;
import org.example.model.thing.type.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Класс, тестирующий добавление и вывод вещей
 */
@ExtendWith(MockitoExtension.class)
class InventoryManagerTest {

    @Mock
    private InMemoryInventoryRepository inventoryRepository;

    private InventoryManager inventoryManager;
    @BeforeEach
    void setUp() {
        inventoryManager = new InventoryManager(inventoryRepository);
    }

    @Test
    void getAllItems_ShouldReturnItemsFromRepository() {
        List<IInventory> expectedItems = List.of(new Table(0), new Computer(0));
        when(inventoryRepository.getAllItems()).thenReturn(expectedItems);
        List<IInventory> result = inventoryManager.getAllItems();

        assertEquals(expectedItems, result);
        verify(inventoryRepository).getAllItems();
    }
}