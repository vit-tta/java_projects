package org.example.service.repository;

import org.example.model.animal.IAlive;

import java.util.List;

public interface AnimalRepository {
    void addAnimal(IAlive animal);
    List<IAlive> getAllAnimals();

}
