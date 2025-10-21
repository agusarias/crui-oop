package com.oop.examples.patterns.dnd.character;

import com.oop.examples.patterns.dnd.visitor.CharacterVisitor;
import com.oop.examples.patterns.dnd.character.EnemyKind;

// Enemies shouldn't have equipment
// import com.oop.examples.patterns.dnd.equipment.Armour;
// import com.oop.examples.patterns.dnd.equipment.Weapon;

static class Enemy extends Character {
    private EnemyKind kind;
    // Enemies shouldn't have equipment
    // private Weapon weapon;
    // private Armour armour;

    public Enemy(String name, EnemyKind kind, int baseAttack, int baseDefense, int health) {
        super(name, health, baseAttack, baseDefense);
        this.kind = kind;
    }

    public EnemyKind getKind() {
        return kind;
    }

    // Enemies shouldn't have equipment
    /* public void setWeapon(Weapon w) {
        this.weapon = w;
    }

    public void setArmour(Armour a) {
        this.armour = a;
    } */

    @override
    public int takeDamage(int damage) {
        // Defense acts to reduce received damage for half of its value, but can't be less than 1
        int mitigated = Math.max(1, damage - (getDefense() / 2));
        // Guarantees that health won't go into the negatives
        health = Math.max(0, health - mitigated);
        return mitigated;
    }

    @Override
    protected CharacterVisitor createVisitor() {
        return new EnemyAttackVisitor(this);
    }
    
    @Override
    public int accept(CharacterVisitor visitor) {
        return visitor.visit(this);
    }
}

// Initialize default Enemy buffs
static {
    // 20% chance for +6 damage (orc bonus, chance-based)
    BuffManager.addBuff(Enemy.class, new Buff("orc_attack_bonus", 6, 20));
    
    // Dragon resistance: -4 damage from non-elf attacks (guaranteed)
    BuffManager.addBuff(Enemy.class, Buff.guaranteed("dragon_resistance", -4));
}