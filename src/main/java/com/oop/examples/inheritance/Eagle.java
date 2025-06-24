package com.oop.examples.inheritance;

public class Eagle extends Bird {

     public Eagle(String name, int age, String species) {
        super(name, age, species);
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " dice: Screee!");
    }

    @Override
    public void fly() {
        System.out.println(getName() + " vuela alto y rápido.");
    }
}
