package com.crui.patterns.examples.dnd.after;

import com.crui.patterns.examples.dnd.after.util.Dice;

public abstract class Character {
  private String name;
  private String kind; // elf, human, dwarf, warrior
  private int baseAttack;
  private int baseDefense;
  private int health;

  public Character(String name, String kind, int baseAttack, int baseDefense, int health) {
    this.name = name;
    this.kind = kind;
    this.baseAttack = baseAttack;
    this.baseDefense = baseDefense;
    this.health = health;
  }

  public String getName() {
    return name;
  }

  public String getKind() {
    return kind;
  }

  public int getHealth() {
    return health;
  }

  public boolean isAlive() {
    return health > 0;
  }

  public int getAttack() {
    return baseAttack;
  }

  public int getDefense() {
    return baseDefense;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public int getAdditionalAttack(Character target) {
    return 0;
  }

  public int getAdditionalDefense(Character aggresor) {
    return 0;
  }

  public int takeDamage(Character aggresor, int attack) {
    int defense = getDefense() + getAdditionalDefense(aggresor);
    int mitigated = Math.max(1, Math.max(1, attack) - (defense / 2));
    this.setHealth(Math.max(0, this.getHealth() - mitigated));
    return mitigated;
  }

  public int hit(Character target) {
    int attack = getAttack() + getAdditionalAttack(target);
    if (Dice.chance(10)) { // 10% critical hit
      attack += 8;
    }
    return target.takeDamage(this, attack);
  }
}
