package com.crui.patterns.examples.dnd.after.util;

import java.util.Random;

public class Dice {
  private static Dice instance;
  private Random random;

  // Singleton
  private Dice() {
    this.random = new Random();
  }

  static Dice getInstance() {
    if (instance == null) {
      instance = new Dice();
    }
    return instance;
  }

  public static boolean chance(int percent) {
    return getInstance().random.nextInt(100) < percent;
  }

  public static int range(int min, int max) {
    return getInstance().random.nextInt(max - min + 1) + min;
  }
}
