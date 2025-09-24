package com.crui.patterns.examples.dnd.after.hero;

public class Armour {
  private String type;
  private int defense;

  public Armour(String type, int defense) {
    this.type = type;
    this.defense = defense;
  }

  public String getType() {
    return type;
  }

  public int getDefense() {
    return defense;
  }
}
