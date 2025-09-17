package com.oop.examples.patterns.dnd;

import java.util.Random;

public class Enemy {
    private String name;
    private String kind; // goblin, orc, dragon...
    private int baseAttack;
    private int baseDefense;
    private int health;
    private Weapon weapon;
    private Armour armour;
    private Random rng = new Random();
    private AttackStrategy attackStrategy;
    private DefenseStrategy defenseStrategy;

    public Enemy(String name, String kind, int baseAttack, int baseDefense, int health) {
        this.name = name;
        this.kind = kind;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.health = health;
        if ("orc".equals(kind)) {
            this.attackStrategy = new OrcAttackStrategy();
            this.defenseStrategy = new OrcDefenseStrategy();
        } else if ("goblin".equals(kind)) {
            this.attackStrategy = new GoblinAttackStrategy();
            this.defenseStrategy = new GoblinDefenseStrategy();
        } else if ("dragon".equals(kind)) {
            this.attackStrategy = new DragonAttackStrategy();
            this.defenseStrategy = new DragonDefenseStrategy();
        } else {
            this.attackStrategy = new HeroAttackStrategy();
            this.defenseStrategy = new HeroDefenseStrategy();
        }
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

    public String getKind() {
        return kind;
    }

    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void setWeapon(Weapon w) {
        this.weapon = w;
    }

    public void setArmour(Armour a) {
        this.armour = a;
    }

    public int getAttack() {
        CharacterContext ctx = new CharacterContext();
        ctx.kind = this.kind;
        ctx.baseAttack = this.baseAttack;
        ctx.weapon = this.weapon;
        return attackStrategy.calculateAttack(ctx);
    }

    public int getDefense() {
        CharacterContext ctx = new CharacterContext();
        ctx.kind = this.kind;
        ctx.baseDefense = this.baseDefense;
        ctx.armour = this.armour;
        return defenseStrategy.calculateDefense(ctx);
    }

    public int takeDamage(int damage) {
        int mitigated = Math.max(1, damage - (getDefense() / 2));
        health = Math.max(0, health - mitigated);
        return mitigated;
    }

    public int hit(Hero target) {
        int atk = getAttack();
        // Los orcos pueden tener un ataque extra el 20% de las veces
        if ("orc".equals(kind) && rng.nextInt(100) < 20) {
            atk += 6;
        }
        atk = Math.max(1, atk);
        int dealt = target.takeDamage(atk);
        return dealt;
    }
}
