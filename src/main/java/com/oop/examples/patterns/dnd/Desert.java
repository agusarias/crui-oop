package com.oop.examples.patterns.dnd;

public class Desert implements Terrain {
    @Override
    public String getName() { return "Desierto"; }
    @Override
    public int modifyAttack(String characterType, int baseAttack) {
        if ("human".equals(characterType)) return baseAttack + 3;
        if ("dwarf".equals(characterType)) return baseAttack - 2;
        return baseAttack;
    }
    @Override
    public int modifyDefense(String characterType, int baseDefense) {
        if ("human".equals(characterType)) return baseDefense + 2;
        return baseDefense;
    }
}
