package com.crui.oop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.crui.oop.composition.Familiy;
import com.crui.oop.encapsulation.Person;
import com.crui.oop.inheritance.Animal;
import com.crui.oop.inheritance.Cat;
import com.crui.oop.inheritance.Dog;
import com.crui.oop.shared.Fotografiable;

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
    Person person1 = new Person("Jorge", 30, "jorge@example.com", "Gomez");
    Person person2 = new Person("Ana", 25, "ana@example.com", "Garcia");
    Person person3 = new Person("Raul", 0, "raul@example.com", "Garcia");
    Person person4 = new Person("Jorge", 43, "jorge@sharktech.com.ar", "Marinoni");
    Person person5 = new Person("Otro", 0, "otro@example.com", "Garcia");
    Person person6 = new Person("Otro", 22, "otro22@example.com", "Garci");
    List<Person> originalMembers =
        new ArrayList<>(Arrays.asList(person1, person2, person3, person4, person5, person6));

    Familiy family = new Familiy(originalMembers);
    System.out.println(family.getMembers());

    // Mostrar mayores de 18
    System.out.println("\n=== Adults in the Family ===");
    List<Person> adults = family.getAdults();
    for (Person adult : adults) {
      System.out.println(adult.getName() + " - " + adult.getAge());
    }
  }

  private static void runEncapsulationExample() {
    // Encapsulation example
    System.out.println("=== Encapsulation Example ===");
    Person person = new Person("John", 30, "john@example.com", "Doe");
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

    fotografiables.add(new Person("John", 30, "john@example.com", "Doe"));
    fotografiables.add(
        new Familiy(
            new ArrayList<Person>(
                Arrays.asList(
                    new Person("John", 30, "john@example.com", "Doe"),
                    new Person("Ana", 25, "ana@example.com", "Garcia")))));
    fotografiables.add(new Dog("Buddy", 3, "Golden Retriever"));
    fotografiables.add(new Cat("Filomena", 2, "Black"));

    fotografiables.get(0).takePicture();

    for (Fotografiable fotografiable : fotografiables) {
      fotografiable.takePicture();
    }
  }
}
