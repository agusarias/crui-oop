package com.oop.examples.patterns.dnd.visitor;

import com.oop.examples.patterns.CharacterVisitor;
// import com.oop.examples.patterns.dnd.DND.Hero;
// import com.oop.examples.patterns.dnd.DND.Enemy;
// import com.oop.examples.patterns.dnd.DND.EnemyKind;
// import com.oop.examples.patterns.dnd.DND.HeroType;
// TODO all these imports
import com.oop.examples.patterns.dnd.utility.RNG;

// Concrete visitor for Enemy attacks
static class EnemyAttackVisitor implements CharacterVisitor {
    private final Enemy attacker;
    
    public EnemyAttackVisitor(Enemy attacker) {
        this.attacker = attacker;
    }

    private boolean shouldApplyOrcBonus() {
        return attacker.getKind() == EnemyKind.ORC && 
            RNG.nextInt(PERCENTAGE_BASE) < ORC_BONUS_CHANCE;
    }

    private int calculateOrcBonus(int baseAttack) {
        return shouldApplyOrcBonus() ? baseAttack + ORC_BONUS_DAMAGE : baseAttack;
    }

    /**
     * Facade method for enemy attacks, similar to the hero's attack sequence.
     * Handles all attack modifications in a clean, readable way.
     */
    @Override
    public int visit(Hero target) {
        // 1. Get base attack value
        int attack = attacker.getAttack();
        
        // 2. Apply all attack buffs (including orc bonus)
        attack = attacker.applyBuffs(attack, "orc_attack_bonus");
        
        // 3. Apply the damage
        return target.takeDamage(attack);
    }

    @Override
    public int visit(Enemy enemy) {
        throw new UnsupportedOperationException("Enemies don't attack other enemies");
    }
}