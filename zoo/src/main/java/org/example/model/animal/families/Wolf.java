package org.example.model.animal.families;

import org.example.model.animal.Predator;

public class Wolf extends Predator {
    public Wolf(int food) {
        super(food);
    }
    @Override
    public String getFamily(){
        return "Волк";
    }
}
