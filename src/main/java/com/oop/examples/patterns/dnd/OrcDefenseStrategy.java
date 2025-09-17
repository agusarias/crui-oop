package com.oop.examples.patterns.dnd;

public class OrcDefenseStrategy implements DefenseStrategy {
    @Override
    public int calculateDefense(CharacterContext context) {
        int armourDef = (context.armour != null) ? context.armour.getDefense() : 0;
        return context.baseDefense + armourDef;
    }
}
