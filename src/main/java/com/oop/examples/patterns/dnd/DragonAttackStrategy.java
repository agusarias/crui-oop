package com.oop.examples.patterns.dnd;

public class DragonAttackStrategy implements AttackStrategy {
    @Override
    public int calculateAttack(CharacterContext context) {
        int weaponAtk = (context.weapon != null) ? context.weapon.getAttack() : 0;
        int atk = context.baseAttack + weaponAtk + 4; // dragon bonus
        return atk;
    }
}
