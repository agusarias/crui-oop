package com.oop.examples.inheritance;

import com.oop.examples.shared.Fotografiable;

public class Bird extends Animal {
    private String species;

    public Bird(String name, int age, String species) {
        super(name, age);
        this.species = species;
    }

    public void fly() {
        System.out.println(getName() + " is flying!");
    }

    public void sing() {
        System.out.println(getName() + " is singing!");
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " chirps!");
    }

    @Override
    public void takePicture() {
        System.out.println("Se tomó una foto de " + getName() + " (" + getClass().getSimpleName() + ")");
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
}
