package com.oop.examples.patterns.dnd.equipment;

static class Armour extends Equipment {

    public Armour(String type, int defense) {
        super(type, defense);
    }

    // This only renames it to represent the intended stat.
    public int getDefense() {
        return super.getStat();
    }
}