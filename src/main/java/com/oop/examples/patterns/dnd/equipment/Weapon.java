package com.oop.examples.patterns.dnd.equipment;

static class Weapon extends Equipment {

    public Weapon(String type, int attack) {
        super(type, attack);
    } 

    // This only renames it to represent the intended stat.
    public int getAttack() {
        return super.getStat();
    }
}