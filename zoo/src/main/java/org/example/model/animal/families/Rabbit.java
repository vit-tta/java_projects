package org.example.model.animal.families;

import org.example.model.animal.Herbo;

public class Rabbit extends Herbo {

    public Rabbit(int food, int kindness) {
        super(food, kindness);
    }
    @Override
    public String getFamily() {
        return "Кролик";
    }
}
