package com.crui.patterns.examples.dnd.after.enemy;

import com.crui.patterns.examples.dnd.after.Character;

public class Dragon extends Enemy {
  public Dragon(String name, String kind, int baseAttack, int baseDefense, int health) {
    super(name, kind, baseAttack, baseDefense, health);
  }

  public int getAdditionalAttack(Character target) {
    return 4;
  }

  @Override
  public String getIcon() {
    return "🐉";
  }
}
