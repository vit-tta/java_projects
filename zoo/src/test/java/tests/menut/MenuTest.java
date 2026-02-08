package tests.menut;

import org.example.service.manager.InputSystem;
import org.example.service.manager.menu.Menu;
import org.example.service.manager.menu.AnimalAddForMenu;
import org.example.service.manager.menu.InventoryAddForMenu;
import org.example.service.manager.menu.ZooControllerForMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

/**
 * Класс, тестирующий меню
 */
@ExtendWith(MockitoExtension.class)
class MenuTest {

    @Mock
    private ZooControllerForMenu zooController;
    @Mock
    private AnimalAddForMenu animalAddForMenu;
    @Mock
    private InventoryAddForMenu inventoryAddForMenu;
    @Mock
    private InputSystem inputSystem;

    private Menu menu;
    @BeforeEach
    void setUp() {
       menu = Menu.builder()
               .inputSystem(inputSystem)
               .zooController(zooController)
               .animalAddForMenu(animalAddForMenu)
               .inventoryAddForMenu(inventoryAddForMenu)
               .build();
    }
    @Test
    void addAnimalShouldCallAnimalAddForMenu() {
        menu.addAnimal();
        verify(animalAddForMenu).addAnimal();
    }
    @Test
    void showAnimalsReportShouldCallZooController() {
        menu.showAnimalsReport();
        verify(zooController).showAnimalsReport();
    }
    @Test
    void showContactZooAnimals_ShouldCallZooController() {
        menu.showContactZooAnimals();
        verify(zooController).showContactZooAnimals();
    }

    @Test
    void addThing_ShouldCallInventoryAddForMenu() {
        menu.addThing();
        verify(inventoryAddForMenu).addThing();
    }

    @Test
    void showInventoryReport_ShouldCallZooController() {
        menu.showInventoryReport();
        verify(zooController).showInventoryReport();
    }

}