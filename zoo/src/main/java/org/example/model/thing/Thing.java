package org.example.model.thing;

public abstract class Thing implements IInventory{
    private int inventoryNumber;

    public Thing(int inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    @Override
    public int getInventoryNumber() {
        return inventoryNumber;
    }

    @Override
    public void setInventoryNumber(int number) {
        this.inventoryNumber = number;
    }

    @Override
    public abstract String getType();
}
