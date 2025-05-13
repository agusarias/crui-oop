package com.oop.examples.inheritance;

public class Hornero extends Bird {
    public Hornero(String name, int age, String species) {
        super(name, age, species);
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " dice: krip!");
    }

    @Override
    public void fly() {
        System.out.println(getName() + " vuela bajo y construye nidos de barro.");
    }
}
