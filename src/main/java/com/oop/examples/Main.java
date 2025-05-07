package com.oop.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.oop.examples.composition.Familiy;
import com.oop.examples.encapsulation.Person;
import com.oop.examples.inheritance.Dog;
import com.oop.examples.shared.Fotografiable;
import com.oop.examples.inheritance.Animal;
import com.oop.examples.inheritance.Cat;

public class Main {
  public static void main(String[] args) {
    // Hay cuatro conceptos principales en POO:
    // 1. Composition
    // 2. Encapsulation
    // 3. Inheritance
    // 4. Polymorphism

    Main.runCompositionExample();
    Main.runEncapsulationExample();
    Main.runInheritanceExample();
    Main.runPolymorphismExample();

    Main.takePictures();
  }

  private static void runCompositionExample() {
    // Composition example
    System.out.println("\n=== Composition Example ===");
    Person person1 = new Person("Jorge Gomez", 30, "jorge@example.com");
    Person person2 = new Person("Ana Garcia", 25, "ana@example.com");
    Person person3 = new Person("Raul Garcia", 0, "raul@example.com");
    Person person4 = new Person("Jorge Marinoni", 43, "jorge@sharktech.com.ar");
    Person person5 = new Person("Otro Garcia", 0, "otro@example.com");
    Person person6 = new Person("Otro Garci 22", 0, "otro22@example.com");
    Person persona7 = new Person("Cosme Fulanito", 33, "raul@example.com");
    List<Person> originalMembers =
        new ArrayList<>(Arrays.asList(person1, person2, person3, person4, person5, person6, persona7));

    Familiy family = new Familiy(originalMembers);
    System.out.println(family.getMembers());

    long mayores = originalMembers.stream()
    .filter(p -> p.getAge() >= 18)
    .count();
    System.out.println("Cantidad de personas mayores de edad: " + mayores);
  }

  private static void runEncapsulationExample() {
    // Encapsulation example
    System.out.println("=== Encapsulation Example ===");
    Person person = new Person("John Doe", 30, "john@example.com");
    person.displayInfo();

    // Try to set invalid data
    person.setAge(-5); // Won't change the age
    person.setEmail("invalid-email"); // Won't change the email
    System.out.println("After invalid updates:");
    person.displayInfo();
  }

  private static void runInheritanceExample() {
    // Inheritance example
    System.out.println("\n=== Inheritance Example ===");
    Dog dog = new Dog("Buddy", 3, "Golden Retriever");

    // Using inherited methods
    dog.makeSound();

    dog.sleep();

    // Using Dog-specific methods
    dog.fetch();
  }

  private static void runPolymorphismExample() {
    // Polymorphism through inheritance
    System.out.println("\n=== Polymorphism through Inheritance ===");
    Animal animal = new Dog("Max", 2, "Labrador");
    animal.makeSound(); // Calls Dog's implementation
    animal.sleep(); // Calls Animal's implementation

    animal = new Cat("Filomena", 2, "Black");
    animal.makeSound(); // Calls Cat's implementation
    animal.sleep(); // Calls Animal's implementation
  }

  private static void takePictures() {
    // Generics
    List<Fotografiable> fotografiables = new ArrayList<Fotografiable>();

    fotografiables.add(new Person("John Doe", 30, "john@example.com"));
    fotografiables.add(
        new Familiy(
            new ArrayList<Person>(
                Arrays.asList(
                    new Person("John Doe", 30, "john@example.com"),
                    new Person("Ana Garcia", 25, "ana@example.com")))));
    fotografiables.add(new Dog("Buddy", 3, "Golden Retriever"));
    fotografiables.add(new Cat("Filomena", 2, "Black"));

    fotografiables.get(0).takePicture();

    for (Fotografiable fotografiable : fotografiables) {
      fotografiable.takePicture();
    }
  }
}
