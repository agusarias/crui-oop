package com.oop.examples.inheritance;

import com.oop.examples.shared.Picture;

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

// orders for the cat to follow
@Override
  public void followOrder(String order) {
    switch (order.toLowerCase()) {
      case "sit":
          System.out.println(name + " sits... and walks away");
          break;
      case "stay":
          System.out.println(name + " ignores me and walks away..");
          break;
      default:
          System.out.println(name + " looks at you with.. disgust?");
    }
}

  // Method specific to Cat class
  public void play() {
    System.out.println(name + " is playing with a ball!");
  }

  public String getColor() {
    return color;
  }
}
