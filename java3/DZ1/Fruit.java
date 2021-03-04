package Lesson_1.DZ;

public abstract class Fruit {
    protected float weight;

    public abstract Fruit newInstance();

    public float getWeight() {
        return weight;
    }

    public Fruit(float weight) {
        this.weight = weight;
    }
}