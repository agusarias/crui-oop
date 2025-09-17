package com.oop.examples.patterns.dnd;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CharacterFactory {
    private static final List<String> HERO_TYPES = Arrays.asList("elf", "human", "dwarf", "warrior");
    private static final List<String> ENEMY_TYPES = Arrays.asList("goblin", "orc", "dragon");
    private static final List<String> WEAPON_TYPES = Arrays.asList("Sword", "Axe", "Bow", "Dagger");
    private static final List<String> ARMOUR_TYPES = Arrays.asList("Leather", "Chainmail", "Plate");
    private static final Random random = new Random();

    public static Hero randomHero() {
        String type = HERO_TYPES.get(random.nextInt(HERO_TYPES.size()));
        String name = "Hero" + (random.nextInt(900) + 100);
        int baseAttack = 15 + random.nextInt(10);
        int baseDefense = 5 + random.nextInt(6);
        int health = 120 + random.nextInt(61);
        Hero hero = new Hero(name, type, baseAttack, baseDefense, health);
        hero.setWeapon(randomWeapon());
        hero.setArmour(randomArmour());
        return hero;
    }

    public static Enemy randomEnemy() {
        String kind = ENEMY_TYPES.get(random.nextInt(ENEMY_TYPES.size()));
        String name = kind.substring(0, 1).toUpperCase() + kind.substring(1) + (random.nextInt(900) + 100);
        int baseAttack = 10 + random.nextInt(13);
        int baseDefense = 3 + random.nextInt(7);
        int health = 40 + random.nextInt(61);
        Enemy enemy = new Enemy(name, kind, baseAttack, baseDefense, health);
        enemy.setWeapon(randomWeapon());
        enemy.setArmour(randomArmour());
        return enemy;
    }

    public static Weapon randomWeapon() {
        String type = WEAPON_TYPES.get(random.nextInt(WEAPON_TYPES.size()));
        int attack = 8 + random.nextInt(8);
        return new Weapon(type, attack);
    }

    public static Armour randomArmour() {
        String type = ARMOUR_TYPES.get(random.nextInt(ARMOUR_TYPES.size()));
        int defense = 3 + random.nextInt(7);
        return new Armour(type, defense);
    }
}
