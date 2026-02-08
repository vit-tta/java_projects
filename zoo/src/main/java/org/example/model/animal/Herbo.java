package org.example.model.animal;

public abstract class Herbo extends Animal{
    private final int levelOfKindness;

    public int getLevelOfKindness() {
        return levelOfKindness;
    }

    public Herbo(int food, int kindness) {
        super(food);
        this.levelOfKindness = kindness;
    }
    @Override
    public boolean canBeInContactZoo() {
        return getHealthStatus() == HealthStatus.HEALTHY && levelOfKindness > 5;
    }
}

