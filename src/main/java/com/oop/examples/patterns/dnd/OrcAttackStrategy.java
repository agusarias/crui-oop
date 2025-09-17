package com.oop.examples.patterns.dnd;

public class OrcAttackStrategy implements AttackStrategy {
    @Override
    public int calculateAttack(CharacterContext context) {
        int weaponAtk = (context.weapon != null) ? context.weapon.getAttack() : 0;
        int atk = context.baseAttack + weaponAtk + 2; // orc bonus
        return atk;
    }
}
