package tests.clinics;

import org.example.model.animal.HealthStatus;
import org.example.model.animal.IAlive;
import org.example.service.clinic.VeterinaryClinic;
import org.example.service.manager.InputSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Класс для проверки здоровья
 */
@ExtendWith(MockitoExtension.class)
public class ClinicTest {
    @Mock
    private IAlive animal;
    @Mock
    private InputSystem inputSystem;

    private VeterinaryClinic veterinaryClinic;
    @BeforeEach
    void setUp() {
        veterinaryClinic = new VeterinaryClinic(inputSystem);
    }

    @Test
    void examineAnimalWhenHealthyShouldSetHealthyStatusAndReturnTrue() {
        when(inputSystem.getInt(1, 2)).thenReturn(1);
        boolean result = veterinaryClinic.examineAnimal(animal);
        assertTrue(result);
        verify(animal).setHealthStatus(HealthStatus.HEALTHY);
        verify(inputSystem).getInt(1, 2);
    }
    @Test
    void examineAnimalWhenSickShouldSetSickStatusAndReturnFalse() {
        when(inputSystem.getInt(1, 2)).thenReturn(2);
        boolean result = veterinaryClinic.examineAnimal(animal);
        assertFalse(result);
        verify(animal).setHealthStatus(HealthStatus.SICK);
        verify(inputSystem).getInt(1, 2);
    }

}
