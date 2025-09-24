package com.crui.patterns.examples.dnd.after.enemy;

import com.crui.patterns.examples.dnd.after.Character;
import com.crui.patterns.examples.dnd.after.util.Dice;

public class Orc extends Enemy {
  public Orc(String name, String kind, int baseAttack, int baseDefense, int health) {
    super(name, kind, baseAttack, baseDefense, health);
  }

  public int getAdditionalAttack(Character target) {
    int base = 2;
    if (Dice.chance(20)) {
      base += 5;
    }
    return base;
  }

  @Override
  public String getIcon() {
    return "👹";
  }
}
