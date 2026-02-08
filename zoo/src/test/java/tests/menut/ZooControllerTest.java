package tests.menut;

import org.example.service.manager.InputSystem;
import org.example.service.manager.InventoryManager;
import org.example.service.manager.AnimalManager;
import org.example.service.manager.menu.ZooControllerForMenu;
import org.example.model.animal.Animal;
import org.example.model.animal.HealthStatus;
import org.example.model.animal.families.Rabbit;
import org.example.model.thing.type.Computer;
import org.example.model.thing.type.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.mockito.Mockito.*;

/**
 * Класс, тестирующий корректный вывод отчетов
 */
@ExtendWith(MockitoExtension.class)
class ZooControllerTest {

    @Mock
    private AnimalManager zooManager;
    @Mock
    private InventoryManager inventoryManager;
    @Mock
    private InputSystem inputSystem;

    private ZooControllerForMenu zooController;
    @BeforeEach
    void setUp() {
        zooController = new ZooControllerForMenu(zooManager, inventoryManager);
    }

    @Test
    void showAnimalsReportShouldPrintReportWithAnimals() {
        Animal rabbit = new Rabbit(2, 7);
        rabbit.setInventoryNumber(0);
        rabbit.setHealthStatus(HealthStatus.HEALTHY);
        when(zooManager.getAllAnimals()).thenReturn(List.of(rabbit));
        when(zooManager.getAnimalCount()).thenReturn(1);
        when(zooManager.getTotalFoodConsumption()).thenReturn(2);
        zooController.showAnimalsReport();
    }

    @Test
    void showContactZooAnimalsShouldPrintContactZooAnimals() {
        Animal rabbit = new Rabbit(2, 7);
        rabbit.setInventoryNumber(0);
        when(zooManager.getAnimalsForContactZoo()).thenReturn(List.of(rabbit));
        zooController.showContactZooAnimals();
    }

    @Test
    void showInventoryReportShouldPrintInventoryReport() {
        Table table = new Table(0);
        table.setInventoryNumber(0);
        Computer computer = new Computer(0);
        computer.setInventoryNumber(1);
        when(inventoryManager.getAllItems()).thenReturn(List.of(table, computer));
        zooController.showInventoryReport();
    }
}
