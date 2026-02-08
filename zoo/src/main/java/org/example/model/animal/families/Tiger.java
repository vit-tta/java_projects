package org.example.model.animal.families;

import org.example.model.animal.Predator;

public class Tiger extends Predator {

    public Tiger(int food) {
        super(food);
    }
    @Override
    public String getFamily(){
        return "Тигр";
    }
}
