package com.oop.examples.patterns.dnd.visitor;

import com.oop.examples.patterns.CharacterVisitor;
// import com.oop.examples.patterns.dnd.DND.Hero;
// import com.oop.examples.patterns.dnd.DND.Enemy;
// import com.oop.examples.patterns.dnd.DND.EnemyKind;
// import com.oop.examples.patterns.dnd.DND.HeroType;
// TODO all these imports
import com.oop.examples.patterns.dnd.utility.RNG;

// Constants
private static final int PERCENTAGE_BASE = 100;
private static final int CRITICAL_HIT_CHANCE = 10;
private static final int CRITICAL_HIT_BONUS = 8;
private static final int ORC_BONUS_CHANCE = 20;
private static final int ORC_BONUS_DAMAGE = 6;
// Check if these are needed here or in The characters classes that uses them.

// Concrete visitor for Hero attacks
static class HeroAttackVisitor implements CharacterVisitor {
    private final Hero attacker;
    
    public HeroAttackVisitor(Hero attacker) {
        this.attacker = attacker;
    }

    private boolean isCriticalHit() {
        return RNG.nextInt(PERCENTAGE_BASE) < CRITICAL_HIT_CHANCE;
    }

    private int calculateCriticalHit(int baseAttack) {
        return isCriticalHit() ? baseAttack + CRITICAL_HIT_BONUS : baseAttack;
    }

    private int calculateDragonResistance(int attack, Enemy target) {
        if (target.getKind() == EnemyKind.DRAGON && attacker.getType() != HeroType.ELF) {
            // Apply dragon resistance buff directly
            return target.applyBuffs(attack, "dragon_resistance");
        }
        return attack;
    }

    @Override
    public int visit(Hero hero) {
        throw new UnsupportedOperationException("Heroes don't attack other heroes");
    }

    /**
     * This method serves as a Facade that orchestrates the attack sequence,
     * hiding the complexity of individual calculations behind simple method calls.
     * The actual calculations are delegated to specialized private methods.
     */
    @Override
    public int visit(Enemy target) {
        // 1. Get base attack value
        int attack = attacker.getAttack();
        
        // 2. Apply all attack buffs (including critical hits)
        attack = attacker.applyBuffs(attack, "attack_crit_chance");
        
        // 3. Apply any target-specific resistances
        attack = calculateDragonResistance(attack, target);
        
        // 4. Ensure minimum damage and apply
        return target.takeDamage(Math.max(1, attack));
    }
}