package org.example.model.thing.type;

import org.example.model.thing.Thing;

public class Table extends Thing {
    public Table(int inventoryNumber) {
        super(inventoryNumber);
    }

    @Override
    public String getType() {
        return "Стол";
    }
}
