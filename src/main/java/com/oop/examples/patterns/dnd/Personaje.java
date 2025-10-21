package com.oop.examples.patterns.dnd;

import java.util.Random;

abstract class Personaje {
    protected String name; // goblin, orc, dragon...
    protected int baseAttack;
    protected int baseDefense;
    protected int health;
    protected Weapon weapon; // la arma que lleva el personaje
    protected Armour armour;
    protected Random rng = new Random();

    public Personaje(String name, int baseAttack, int baseDefense, int health, Weapon weapon,
            Armour armour) {
        this.name = name;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.health = health;
        this.weapon = weapon;
        this.armour = armour;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public int getHealth() {
        return health;

    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Armour getArmour() {
        return armour;
    }

    // Setters
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void setArmour(Armour armour) {
        this.armour = armour;
    }

    // Métodos que las subclases pueden usar
    public int getDefense() {
        int armourDef = (armour != null) ? armour.getDefense() : 0;
        return baseDefense + armourDef;
    }

    public int takeDamage(int damage) {
        int mitigated = Math.max(1, damage - (getDefense() / 2));
        health = Math.max(0, health - mitigated);
        return mitigated;
    }

    public boolean isAlive() {
        return health > 0;
    }

    // Método abstracto que las subclases deben implementar
    public abstract int getAttack();

    public abstract int hit(Personaje target);
}
