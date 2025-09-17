package com.oop.examples.patterns.dnd;

import java.util.Random;

public class GoblinAttackStrategy implements AttackStrategy {
    private Random rng = new Random();
    @Override
    public int calculateAttack(CharacterContext context) {
        int weaponAtk = (context.weapon != null) ? context.weapon.getAttack() : 0;
        int atk = context.baseAttack + weaponAtk;
        // goblin más débil pero esquiva a veces
        if (rng.nextInt(100) < 15) {
            atk += 5;
        }
        return atk;
    }
}
