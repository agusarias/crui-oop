package com.oop.examples.inheritance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.crui.oop.inheritance.Animal;
import com.crui.oop.inheritance.Dog;

import org.junit.jupiter.api.DisplayName;

/** Test class for Dog. */
public class DogTest {

  private Dog dog;

  @BeforeEach
  void setUp() {
    // Initialize a new Dog instance before each test
    dog = new Dog("Buddy", 3, "Golden Retriever");
  }

  @Test
  @DisplayName("Test dog creation with proper attributes")
  void testDogCreation() {
    assertEquals("Buddy", dog.getName());
    assertEquals(3, dog.getAge());
    assertEquals("Golden Retriever", dog.getBreed());
  }

  @Test
  @DisplayName("Test inheritance from Animal class")
  void testInheritance() {
    // Verify that Dog is an instance of Animal
    assertNotNull(dog);
    assertEquals(Animal.class, dog.getClass().getSuperclass());
  }

  @Test
  @DisplayName("Test dog specific methods")
  void testDogSpecificMethods() {
    // Just a simple test to ensure the method runs without exceptions
    dog.fetch();
  }
}
