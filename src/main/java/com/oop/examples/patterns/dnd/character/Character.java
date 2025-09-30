package com.oop.examples.patterns.dnd.character;

import com.oop.examples.patterns.dnd.Buffable;
import com.oop.examples.patterns.dnd.visitor.CharacterVisitor;

// Abstract class for characters
abstract static class Character implements Buffable {
    protected String name;
    protected int health;
    protected int baseAttack;
    protected int baseDefense;

    public Character(String name, int health, int attack, int defense) {
        this.name = name;
        this.health = valueValidator(health);
        this.baseAttack = valueValidator(attack);
        this.baseDefense = valueValidator(defense);
    }

    // If value is not valid, defaults to 1.
    private int valueValidator (int value) {
        if (value > 0) {
            return value;
        } else {
            return 1;
        }
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return baseAttack;
    }

    public int getDefense() {
        return baseDefense;
    }

    public boolean isAlive() {
        return 0<health;
    }

    public abstract int accept(CharacterVisitor visitor);
    public abstract int takeDamage(int damage);
    
    // Template method for hit that uses the visitor pattern
    public final int hit(Character target) {
        return target.accept(createVisitor());
    }
    
    // Factory method for creating the appropriate visitor
    protected abstract CharacterVisitor createVisitor();
}