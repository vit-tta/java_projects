package tests.menut;

import org.example.model.animal.families.Tiger;
import org.example.service.manager.InputSystem;
import org.example.service.manager.menu.AnimalAddForMenu;
import org.example.service.manager.menu.ZooControllerForMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalAddForMenuTest {
    @Mock
    private ZooControllerForMenu zooController;
    @Mock
    private InputSystem inputSystem;

    private AnimalAddForMenu animalAddForMenu;
    @BeforeEach
    void setUp() {
        animalAddForMenu = new AnimalAddForMenu(zooController, inputSystem);
    }

    @Test
    void addAnimalWithWrongClassificationShouldntAddAnimal() {
        when(inputSystem.getInt(Mockito.anyInt(), Mockito.anyInt())).thenReturn(-5);
        animalAddForMenu.addAnimal();
        verify(zooController, never()).addAnimal(any());
    }
    @Test
    void createPredatorWhenValidInput_ShouldReturnAnimal() {
        when(inputSystem.getInt(1, 2)).thenReturn(1);
        when(inputSystem.getInt(1, Integer.MAX_VALUE)).thenReturn(20);
        when(inputSystem.getInt(1, 2)).thenReturn(1, 1);
        animalAddForMenu.addAnimal();
        verify(zooController).addAnimal(any(Tiger.class));
    }
}
