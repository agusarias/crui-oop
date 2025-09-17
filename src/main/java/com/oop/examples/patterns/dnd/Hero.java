package com.oop.examples.patterns.dnd;

public class Hero extends Personaje {

  private String type; // elf, human, dwarf, warrior

  public Hero(String name, String type, int baseAttack, int baseDefense, int health) {
    super(name, baseAttack, baseDefense, health, null, null);
    this.type = type;
  }

  public String getType() {
    return type;
  }


  @Override
  public int getAttack() {
    int weaponAtk = (weapon != null) ? weapon.getAttack() : 0;
    if ("warrior".equals(type)) {
      weaponAtk += 2;
    }
    return baseAttack + weaponAtk;
  }


  @Override
  public int hit(Personaje target) {
    int atk = getAttack();
    if (rng.nextInt(100) < 10) { // 10% critical hit
      atk += 8;
    }
    // Dragon es resistente a los no elfos
    if (target instanceof Enemy && "dragon".equals(((Enemy) target).getKind()) && !"elf".equals(type)) {
      atk -= 4;
    }
    atk = Math.max(1, atk);
    int dealt = target.takeDamage(atk);
    return dealt;
  }

}