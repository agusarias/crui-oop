package com.crui.patterns.examples.dnd.after.enemy;

import com.crui.patterns.examples.dnd.after.Character;

public class Enemy extends Character {
  public Enemy(String name, String kind, int baseAttack, int baseDefense, int health) {
    super(name, kind, baseAttack, baseDefense, health);
  }

  public String getIcon() {
    return "🧌";
  }
}
