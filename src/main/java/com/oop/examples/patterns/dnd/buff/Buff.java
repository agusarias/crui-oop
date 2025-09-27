package com.oop.examples.patterns.dnd.buff;
import com.oop.examples.patterns.dnd.utility.RNG; // Random number generator

/**
* Represents a single buff effect with a chance to be applied.
* @param effectType Type of effect (e.g., "attack", "defense")
* @param value The value to add/subtract when buff activates
* @param chance Percentage chance (0-100) for the buff to activate, 100 means always applies
*/
record Buff(String effectType, int value, int chance) {
    private static final int PERCENTAGE_BASE = 100;
    
    /**
     * Creates a guaranteed effect (100% chance)
     */
    static Buff guaranteed(String effectType, int value) {
    return new Buff(effectType, value, 100);
    }

    int apply(int base) {
    return chance >= 100 ? base + value : 
            RNG.nextInt(PERCENTAGE_BASE) < chance ?
            base + value
            : base;
    }
}