package com.crui.oop.inheritance;

import com.crui.oop.shared.Picture;

public class Topo extends Animal {
  private String breed;

  public Topo(String name, int age) {
    super(name, age);
  }

  // Implementation of abstract method from Animal class
  @Override
  public void makeSound() {
    System.out.println(name + " says: ???!");
  }

  @Override
  public Picture takePicture() {
    return new Picture(100, 120, "jpg");
  }

  // Method specific to Dog class
  public void fetch() {
    System.out.println(name + " is fetching the ball!");
  }

  public String getBreed() {
    return breed;
  }

  // Method overloading - same method name, different parameters
  public void play(String toy) {
    System.out.println(name + " is playing with " + toy);
  }

  public void play(String toy, int duration) {
    System.out.println(name + " is playing with " + toy + " for " + duration + " minutes");
  }

  public String toString() {
    return "Topo [name=" + name + ", age=" + age + ", breed=" + breed + "]";
  }
}
