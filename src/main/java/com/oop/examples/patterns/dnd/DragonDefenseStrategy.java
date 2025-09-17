package com.oop.examples.patterns.dnd;

public class DragonDefenseStrategy implements DefenseStrategy {
    @Override
    public int calculateDefense(CharacterContext context) {
        int armourDef = (context.armour != null) ? context.armour.getDefense() : 0;
        int def = context.baseDefense + armourDef + 3; // dragon bonus
        return def;
    }
}
