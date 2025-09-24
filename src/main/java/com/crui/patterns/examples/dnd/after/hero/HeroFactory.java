package com.crui.patterns.examples.dnd.after.hero;

import com.crui.patterns.examples.dnd.after.util.Dice;

public class HeroFactory {

  private static final String[] WEAPON_TYPES = {"Sword", "Bow", "Axe", "Mace", "Dagger"};
  private static final String[] ARMOUR_TYPES = {"Leather", "Chainmail", "Plate", "Shield"};

  private static final String[] HERO_NAMES = {"Artemis", "Ares", "Athena", "Hephaestus", "Apollo"};

  static final int BASE = 10;
  static final int RANGE = 10;

  public static Hero createRandomHero() {
    Weapon sword = createRandomWeapon();
    Armour leather = createRandomArmour();

    int baseAttack = Dice.range(10, 20);
    int baseDefense = Dice.range(BASE, BASE + RANGE);
    int health = Dice.range(BASE * 30, BASE * 30 + RANGE * 10);
    Kinds kind = Kinds.values()[Dice.range(0, Kinds.values().length - 1)];
    String name = HERO_NAMES[Dice.range(0, HERO_NAMES.length - 1)];

    Hero hero;
    switch (kind) {
      case WARRIOR:
        hero = new Warrior(name, kind.getKind(), baseAttack, baseDefense, health);
        break;
      case ELF:
        hero = new Elf(name, kind.getKind(), baseAttack, baseDefense, health);
        break;
      default:
        hero = new Hero(name, kind.getKind(), baseAttack, baseDefense, health);
        break;
    }
    hero.setWeapon(sword);
    hero.setArmour(leather);
    return hero;
  }

  private static Weapon createRandomWeapon() {
    return new Weapon(
        WEAPON_TYPES[Dice.range(0, WEAPON_TYPES.length - 1)],
        Dice.range(BASE / 2, BASE / 2 + RANGE));
  }

  private static Armour createRandomArmour() {
    return new Armour(
        ARMOUR_TYPES[Dice.range(0, ARMOUR_TYPES.length - 1)],
        Dice.range(BASE / 2, BASE / 2 + RANGE));
  }
}
