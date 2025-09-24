package com.oop.examples;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import com.crui.oop.Main;

import org.junit.jupiter.api.DisplayName;

/** Test class for the Main application. */
public class MainTest {

  @Test
  @DisplayName("Test that main method doesn't throw exceptions")
  void testMainMethod() {
    // Test that the main method can be executed without throwing exceptions
    assertDoesNotThrow(() -> Main.main(new String[] {}));
  }
}
