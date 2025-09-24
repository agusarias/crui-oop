package com.crui.patterns.examples.dnd.after.hero;

import com.crui.patterns.examples.dnd.after.Character;

public class Hero extends Character {
  private Weapon weapon;
  private Armour armour;

  public Hero(String name, String kind, int baseAttack, int baseDefense, int health) {
    super(name, kind, baseAttack, baseDefense, health);
  }

  public void setWeapon(Weapon w) {
    this.weapon = w;
  }

  public void setArmour(Armour a) {
    this.armour = a;
  }

  public int getAdditionalAttack(Character target) {
    int base = this.weapon != null ? this.weapon.getAttack() : 0;
    if ("dragon".equals(target.getKind()) && !"elf".equals(target.getKind())) {
      base += 4;
    }
    return base;
  }

  public int getAdditionalDefense(Character aggresor) {
    int base = this.armour != null ? this.armour.getDefense() : 0;
    return base;
  }

  public String getIcon() {
    return "👤";
  }
}
