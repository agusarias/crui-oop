package com.crui.patterns.examples.dnd.after.hero;

import com.crui.patterns.examples.dnd.after.Character;

public class Warrior extends Hero {
  public Warrior(String name, String kind, int baseAttack, int baseDefense, int health) {
    super(name, kind, baseAttack, baseDefense, health);
  }

  public int getAdditionalAttack(Character target) {
    return super.getAdditionalAttack(target) + 2;
  }

  public int getAdditionalDefense(Character aggresor) {
    return super.getAdditionalDefense(aggresor) + 2;
  }

  @Override
  public String getIcon() {
    return "🗡️";
  }
}
