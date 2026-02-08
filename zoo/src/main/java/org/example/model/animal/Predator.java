package org.example.model.animal;

public abstract class Predator extends Animal{

    public Predator(int food) {
        super(food);
    }
    @Override
    public boolean canBeInContactZoo(){
        return false;
    }
}
