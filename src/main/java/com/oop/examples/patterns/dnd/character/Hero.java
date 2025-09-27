package com.oop.examples.patterns.dnd.character;

import com.oop.examples.patterns.dnd.visitor.CharacterVisitor;
import com.oop.examples.patterns.dnd.character.HeroType;
import com.oop.examples.patterns.dnd.equipment.Armour;
import com.oop.examples.patterns.dnd.equipment.Weapon;


static class Hero extends Character {
    private HeroType type;
    private Weapon weapon;
    private Armour armour;
    
    public Hero(String name, HeroType type, int baseAttack, int baseDefense, int health) {
        super(name, health, baseAttack, baseDefense);
        this.type = type;
    }
    
    public void setWeapon(Weapon w) {
        this.weapon = w;
    }
    
    public void setArmour(Armour a) {
        this.armour = a;
    }
    
    public HeroType getType() {
        return type;
    }
    
    @Override
    public int getAttack() {
        // Start with Hero's base attack
        int weaponAtk = this.getAttack();
        // Warrior type deals +2 BD
        if (this.type == HeroType.WARRIOR) {
            weaponAtk += 2;
        }
        // Add WD if any, just base attack otherwise
        return (this.weapon != null) ?
        this.weapon.getAttack() + weaponAtk :
        weaponAtk;
    }
    
    @Override
    public int getDefense() {
        return (this.armour != null) ?
        this.armour.getDefense() + this.baseDefense
        : this.baseDefense;
    }
    
    @Override
    public int takeDamage(int damage) {
        // Defense acts to reduce received damage for half of its value, but can't be less than 1
      int mitigated = Math.max(1, damage - (getDefense() / 2));
      // Guarantees that health won't go into the negatives
      health = Math.max(0, health - mitigated);
      return mitigated;
    }
    
    @Override
    protected CharacterVisitor createVisitor() {
      return new HeroAttackVisitor(this);
    }
    
    @Override
    public int accept(CharacterVisitor visitor) {
      return visitor.visit(this);
    }
}

// Initialize default Hero buffs
static {
    // 10% chance for +8 damage (critical hit)
    BuffManager.addBuff(Hero.class, new Buff("attack_crit_chance", 8, 10));
}