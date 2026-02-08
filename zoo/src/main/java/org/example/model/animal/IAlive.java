package org.example.model.animal;

public interface IAlive {
    int getAmountOfFood();
    void setAmountOfFood(int food);
    int getInventoryNumber();
    void setInventoryNumber(int number);
    HealthStatus getHealthStatus();
    void setHealthStatus(HealthStatus healthStatus);
    boolean canBeInContactZoo();
    String getFamily();
}

