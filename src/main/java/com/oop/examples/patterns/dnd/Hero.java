package com.oop.examples.patterns.dnd;

import java.util.Random;

public class Hero {
    private String name;
    private String type; // elf, human, dwarf, warrior
    private int baseAttack;
    private int baseDefense;
    private int health;
    private Weapon weapon;
    private Armour armour;
    private Random random = new Random();
    private AttackStrategy attackStrategy;
    private DefenseStrategy defenseStrategy;

    public Hero(String name, String type, int baseAttack, int baseDefense, int health) {
        this.name = name;
        this.type = type;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.health = health;
        this.attackStrategy = new HeroAttackStrategy();
        this.defenseStrategy = new HeroDefenseStrategy();
    }

    public void setWeapon(Weapon w) {
        this.weapon = w;
    }

    public void setArmour(Armour a) {
        this.armour = a;
    }

    public void setAttackStrategy(AttackStrategy strategy) {
        this.attackStrategy = strategy;
    }

    public void setDefenseStrategy(DefenseStrategy strategy) {
        this.defenseStrategy = strategy;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getAttack() {
        CharacterContext ctx = new CharacterContext();
        ctx.type = this.type;
        ctx.baseAttack = this.baseAttack;
        ctx.weapon = this.weapon;
        return attackStrategy.calculateAttack(ctx);
    }

    public int getDefense() {
        CharacterContext ctx = new CharacterContext();
        ctx.type = this.type;
        ctx.baseDefense = this.baseDefense;
        ctx.armour = this.armour;
        return defenseStrategy.calculateDefense(ctx);
    }

    public int takeDamage(int damage) {
        int mitigated = Math.max(1, damage - (getDefense() / 2));
        health = Math.max(0, health - mitigated);
        return mitigated;
    }

    public int hit(Enemy target) {
        int atk = getAttack();
        if (random.nextInt(100) < 10) { // 10% critical hit
            atk += 8;
        }
        // Dragon es resistente a los no elfos
        if ("dragon".equals(target.getKind()) && !"elf".equals(type)) {
            atk -= 4;
        }
        atk = Math.max(1, atk);
        int dealt = target.takeDamage(atk);
        return dealt;
    }
}
