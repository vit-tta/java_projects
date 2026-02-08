package tests.menut;

import org.example.service.manager.InputSystem;
import org.example.service.manager.menu.InventoryAddForMenu;
import org.example.service.manager.menu.ZooControllerForMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryAddForMenuTest {

    @Mock
    private ZooControllerForMenu zooController;
    @Mock
    private InputSystem inputSystem;

    private InventoryAddForMenu inventoryAddForMenu;
    @BeforeEach
    void setUp() {
        inventoryAddForMenu = new InventoryAddForMenu(zooController, inputSystem);
    }

    @Test
    void addInventoryWithWrongClassificationShouldntAdd(){
        when(inputSystem.getInt(Mockito.anyInt(), Mockito.anyInt())).thenReturn(-5);
        inventoryAddForMenu.addThing();
        verify(zooController, never()).addThing(any());
    }
}
