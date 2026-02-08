package org.example.model.thing.type;

import org.example.model.thing.Thing;

public class Computer extends Thing {
    public Computer(int inventoryNumber) {
        super(inventoryNumber);
    }

    @Override
    public String getType() {
        return "Компьютер";
    }
}
