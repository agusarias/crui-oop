package com.oop.examples.patterns.dnd.factory;

import com.oop.examples.patterns.dnd.character.*;   // All characters
import com.oop.examples.patterns.dnd.equipment.*;   // All equipment

import com.oop.examples.patterns.utility.RNG;

import java.util.Arrays;
import java.util.List;

/**
 * Implementation of factories for creating random game elements including characters and equipment.
 */
public class RandomDNDFactory implements CharacterFactory, EquipmentFactory {
    private static final List<String> HERO_NAMES = Arrays.asList(
        "Aragorn", "Legolas", "Gandalf", "Gimli", "Frodo",
        "Samwise", "Merry", "Pippin", "Boromir", "Faramir"
    );
    
    private static final List<String> ENEMY_NAMES = Arrays.asList(
        "Goblin", "Orc", "Troll", "Warg", "Uruk-hai",
        "Nazgul", "Balrog", "Dragon", "Spider", "Witch-king"
    );

    // ========== CharacterFactory Implementation ==========

    
    // Generates a random hero with 50% chance of having a weapon and armour each.
    @Override
    public static Hero createRandomHero() {
        return createRandomHero(RNG.nextBoolean(), RNG.nextBoolean());
    }

    public static Hero createRandomHero(boolean withWeapon, boolean withArmour) {
        String name = HERO_NAMES.get(RNG.nextInt(HERO_NAMES.size()));
        HeroType type = HeroType.values()[RNG.nextInt(HeroType.values().length)];
        
        // Generate random stats based on existing game balance
        int attack = 5 + RNG.nextInt(10);   // 5-15
        int defense = 3 + RNG.nextInt(8);   // 3-11
        int health = 15 + RNG.nextInt(30);  // 15-45
        
        Hero hero = new Hero(name, type, attack, defense, health);
        
        if (withWeapon) {
            hero.setWeapon(createRandomWeapon());
        }
        if (withArmour) {
            hero.setArmour(createRandomArmour());
        }
        
        return hero;
    }

    @Override
    public static Character createRandomEnemy() {
        String name = ENEMY_NAMES.get(RNG.nextInt(ENEMY_NAMES.size()));
        EnemyKind kind = EnemyKind.values()[RNG.nextInt(EnemyKind.values().length)];
        
        // Generate random stats based on enemy kind
        int attack, defense, health;
        switch (kind) {
            case GOBLIN:
                attack = 3 + RNG.nextInt(5);    // 3-8
                defense = 2 + RNG.nextInt(4);   // 2-6
                health = 10 + RNG.nextInt(15);  // 10-25
                break;
            case ORC:
                attack = 5 + RNG.nextInt(8);    // 5-13
                defense = 4 + RNG.nextInt(6);   // 4-10
                health = 15 + RNG.nextInt(20);  // 15-35
                break;
            case DRAGON:
                attack = 8 + RNG.nextInt(12);   // 8-20
                defense = 6 + RNG.nextInt(10);  // 6-16
                health = 30 + RNG.nextInt(40);  // 30-70
                break;
            default:
                attack = 4 + RNG.nextInt(6);    // 4-10
                defense = 3 + RNG.nextInt(5);   // 3-8
                health = 12 + RNG.nextInt(20);  // 12-32
        }
        
        return new Enemy(name, kind, attack, defense, health);
    }

    // ========== EquipmentFactory Implementation ==========

    @Override
    public static Weapon createRandomWeapon() {
        String[] weaponTypes = {"Sword", "Axe", "Mace", "Dagger", "Bow"};
        String type = weaponTypes[RNG.nextInt(weaponTypes.length)];
        int attack = 5 + RNG.nextInt(10);  // 5-15 attack
        return new Weapon(type, attack);
    }
    
    @Override
    public static Armour createRandomArmour() {
        String[] armourTypes = {"Leather", "Chainmail", "Plate", "Scale"};
        String type = armourTypes[RNG.nextInt(armourTypes.length)];
        int defense = 2 + RNG.nextInt(6);  // 2-8 defense
        return new Armour(type, defense);
    }
}