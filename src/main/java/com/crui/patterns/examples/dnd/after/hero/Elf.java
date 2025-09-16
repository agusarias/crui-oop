package com.crui.patterns.examples.dnd.after.hero;

import com.crui.patterns.examples.dnd.after.Character;

public class Elf extends Hero {
  public Elf(String name, String kind, int baseAttack, int baseDefense, int health) {
    super(name, kind, baseAttack, baseDefense, health);
  }

  public int getAdditionalAttack(Character target) {
    int base = target.getKind().equals("dragon") ? 4 : 0;
    return super.getAdditionalAttack(target) + base;
  }

  @Override
  public String getIcon() {
    return "🧝🏻";
  }
}
