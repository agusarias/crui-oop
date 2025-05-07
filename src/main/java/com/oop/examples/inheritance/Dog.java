package com.oop.examples.inheritance;

import com.oop.examples.shared.Picture;

public class Dog extends Animal {
  private String breed;

  public Dog(String name, int age, String breed) {
    super(name, age);
    this.breed = breed;
  }

  // Implementation of abstract method from Animal class
  @Override
  public void makeSound() {
    System.out.println(name + " says: Woof!");
  }

  @Override
  public Picture takePicture() {
    return new Picture(100, 120, "jpg");
  }

// orders for the dog to follow
@Override
  public void followOrder(String order) {
    switch (order.toLowerCase()) {
        case "sit":
            System.out.println(name + " is now sitting :D!");
            break;
        case "stay":
            System.out.println(name + " is staying stil..");
            break;
        case "fetch":
            fetch();
            break;
        default:
            System.out.println(name + " tilts head with a look of confusion.");
    }
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
}
