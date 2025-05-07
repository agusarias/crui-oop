package com.oop.examples.inheritance;

import com.oop.examples.shared.Fotografiable;

public abstract class Animal implements Fotografiable {
  protected String name;
  protected int age;

  public Animal(String name, int age) {
    this.name = name;
    this.age = age;
  }

  // Abstract method - must be implemented by subclasses
  public abstract void makeSound();

  // Concrete method - can be used by all subclasses
  public void sleep() {
    System.out.println(name + " is sleeping...");
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }
  
  public void followOrder(String order) {
    System.out.println(this.name + " doesn't understand the order.");
  }
}
