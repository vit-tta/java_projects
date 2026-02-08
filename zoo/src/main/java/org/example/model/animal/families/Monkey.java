package org.example.model.animal.families;

import org.example.model.animal.Herbo;

public class Monkey extends Herbo {
    public Monkey(int food, int kindness) {
        super(food, kindness);
    }
    @Override
    public String getFamily(){
        return "Обезьяна";
    }
}
