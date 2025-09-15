package com.crui.patterns.examples.dnd.after.hero;

public class Weapon {
  private String type;
  private int attack;

  public Weapon(String type, int attack) {
    this.type = type;
    this.attack = attack;
  }

  public String getType() {
    return type;
  }

  public int getAttack() {
    return attack;
  }
}
