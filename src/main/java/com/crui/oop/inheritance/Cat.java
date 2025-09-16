package com.crui.oop.inheritance;

import com.crui.oop.shared.Picture;

public class Cat extends Animal {
  private String color;

  public Cat(String name, int age, String color) {
    super(name, age);
    this.color = color;
  }

  // Implementation of abstract method from Animal class
  @Override
  public void makeSound() {
    System.out.println(name + " says: Meow!");
  }

  @Override
  public Picture takePicture() {
    return new Picture(100, 100, "jpg");
  }

  // Method specific to Cat class
  public void play() {
    System.out.println(name + " is playing with a ball!");
  }

  public String getColor() {
    return color;
  }

  public String toString() {
    return "Cat [name=" + name + ", age=" + age + ", color=" + color + "]";
  }
}
