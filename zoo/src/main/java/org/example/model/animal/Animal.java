package org.example.model.animal;

public abstract class Animal implements IAlive {
    private int amountOfFood;
    private HealthStatus healthStatus;
    private int inventoryNumber;

    public Animal(int food) {
        this.amountOfFood = food;
        this.healthStatus = HealthStatus.SICK;
    }
    @Override
    public int getAmountOfFood(){
        return this.amountOfFood;
    }
    @Override
    public void setAmountOfFood(int food){
        this.amountOfFood=food;
    }
    @Override
    public int getInventoryNumber() {
        return inventoryNumber;
    }
    @Override
    public void setInventoryNumber(int number) {
        this.inventoryNumber = number;
    }
    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }

    public abstract boolean canBeInContactZoo();

    public abstract String getFamily();
}
