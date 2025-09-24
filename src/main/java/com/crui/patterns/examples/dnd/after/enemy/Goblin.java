package com.crui.patterns.examples.dnd.after.enemy;

import com.crui.patterns.examples.dnd.after.Character;
import com.crui.patterns.examples.dnd.after.util.Dice;

public class Goblin extends Enemy {
  public Goblin(String name, String kind, int baseAttack, int baseDefense, int health) {
    super(name, kind, baseAttack, baseDefense, health);
  }

  public int getAdditionalAttack(Character target) {
    int base = 1;
    if (Dice.chance(15)) {
      base += 3;
    }
    return base;
  }

  @Override
  public String getIcon() {
    return "👺";
  }
}
