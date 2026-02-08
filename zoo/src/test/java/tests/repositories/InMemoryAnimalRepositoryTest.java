package tests.repositories;

import org.example.model.animal.IAlive;
import org.example.service.repository.InMemoryAnimalRepository;
import org.example.model.animal.Animal;
import org.example.model.animal.families.Rabbit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Класс, тестирующий добавление животного и присваивание ему номера
 */
class InMemoryAnimalRepositoryTest {

    private InMemoryAnimalRepository inMemoryAnimalRepository;

    @BeforeEach
    void setUp() {
        inMemoryAnimalRepository = new InMemoryAnimalRepository();
    }

    @Test
    void addAnimalShouldAssignInventoryNumberAndAddToList() {
        Animal rabbit = new Rabbit(2, 7);
        inMemoryAnimalRepository.addAnimal(rabbit);

        List<IAlive> animals = inMemoryAnimalRepository.getAllAnimals();
        assertEquals(1, animals.size());
        assertEquals(0, rabbit.getInventoryNumber());
        assertEquals(rabbit, animals.get(0));
    }
}
