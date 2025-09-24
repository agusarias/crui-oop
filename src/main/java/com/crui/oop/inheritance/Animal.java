package com.crui.oop.inheritance;

import com.crui.oop.shared.Fotografiable;

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

  public String toString() {
    return "Animal [name=" + name + ", age=" + age + "]";
  }
}
