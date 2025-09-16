package com.oop.examples.encapsulation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.crui.oop.encapsulation.Person;

import org.junit.jupiter.api.DisplayName;

/** Test class for Person. */
public class PersonTest {

  private Person person;

  @BeforeEach
  void setUp() {
    // Initialize a new Person instance before each test
    person = new Person("Test User", 25, "test@example.com", "Doe");
  }

  @Test
  @DisplayName("Test person creation with valid data")
  void testPersonCreation() {
    assertEquals("Test User", person.getName());
    assertEquals(25, person.getAge());
    assertEquals("test@example.com", person.getEmail());
  }

  @Test
  @DisplayName("Test email validation")
  void testEmailValidation() {
    // Should not change the email as it's invalid
    person.setEmail("invalid-email");
    assertEquals("test@example.com", person.getEmail());

    // Should change the email as it's valid
    person.setEmail("valid@example.com");
    assertEquals("valid@example.com", person.getEmail());
  }

  @Test
  @DisplayName("Test age validation")
  void testAgeValidation() {
    // Should not change the age as it's negative
    person.setAge(-5);
    assertEquals(25, person.getAge());

    // Should change the age as it's valid
    person.setAge(30);
    assertEquals(30, person.getAge());
  }
}
