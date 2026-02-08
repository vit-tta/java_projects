package tests.managers;

import org.example.model.animal.IAlive;
import org.example.service.clinic.VeterinaryClinic;
import org.example.service.manager.AnimalManager;
import org.example.service.repository.InMemoryAnimalRepository;
import org.example.model.animal.Animal;
import org.example.model.animal.families.Rabbit;
import org.example.model.animal.families.Tiger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Класс, тестирующий добавление в репозиторий и вывод информации
 */
@ExtendWith(MockitoExtension.class)
class ZooManagerTest {

    @Mock
    private InMemoryAnimalRepository inMemoryAnimalRepository;

    @Mock
    private VeterinaryClinic veterinaryClinic;

    private AnimalManager zooManager;
    @BeforeEach
    void setUp() {
        zooManager = new AnimalManager(inMemoryAnimalRepository, veterinaryClinic);
    }

    @Test
    void addAnimal_WhenAnimalIsHealthy_ShouldAddToRepository() {
        Animal rabbit = new Rabbit(2, 7);
        when(veterinaryClinic.examineAnimal(rabbit)).thenReturn(true);
        boolean result = zooManager.addAnimal(rabbit);

        assertTrue(result);
        verify(inMemoryAnimalRepository).addAnimal(rabbit);
    }

    @Test
    void addAnimal_WhenAnimalIsNotHealthy_ShouldNotAddToRepository() {
        Animal tiger = new Tiger(10);
        when(veterinaryClinic.examineAnimal(tiger)).thenReturn(false);
        boolean result = zooManager.addAnimal(tiger);

        assertFalse(result);
        verify(inMemoryAnimalRepository, never()).addAnimal(any());
    }

    @Test
    void getAllAnimals_ShouldReturnAnimalsFromRepository() {
        List<IAlive> expectedAnimals = List.of(new Rabbit(2, 7), new Tiger(10));
        when(inMemoryAnimalRepository.getAllAnimals()).thenReturn(expectedAnimals);
        List<IAlive> result = zooManager.getAllAnimals();

        assertEquals(expectedAnimals, result);
        verify(inMemoryAnimalRepository).getAllAnimals();
    }
}