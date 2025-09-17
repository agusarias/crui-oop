package com.oop.examples.patterns.dnd;

public class HeroAttackStrategy implements AttackStrategy {
    @Override
    public int calculateAttack(CharacterContext context) {
        int weaponAtk = (context.weapon != null) ? context.weapon.getAttack() : 0;
        if ("warrior".equals(context.type)) {
            weaponAtk += 2;
        }
        return context.baseAttack + weaponAtk;
    }
}
