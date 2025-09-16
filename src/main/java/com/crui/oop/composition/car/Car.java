package com.crui.oop.composition.car;

import java.util.List;

public class Car {
  private Engine engine;
  private List<Wheel> wheels;
  private String brand;
  private int year;
  private String color;

  public Car(Engine engine, List<Wheel> wheels, String brand, int year, String color) {
    this.engine = engine;
    this.wheels = wheels;
    this.brand = brand;
    this.year = year;
    this.color = color;
  }

  public void turnOn() {
    engine.start();
    System.out.println("You turned on the car...");
  }

  public void turnOff() {
    engine.stop();
    System.out.println("You turned off the car...");
  }

  public void drive() {
    System.out.println("You're driving...");
  }

  public void brake() {
    System.out.println("The car has stopped...");
  }
}
