package com.oop.examples.patterns.dnd;

public class Swamp implements Terrain {
    @Override
    public String getName() { return "Pantano"; }
    @Override
    public int modifyAttack(String characterType, int baseAttack) {
        if ("orc".equals(characterType)) return baseAttack + 3;
        if ("elf".equals(characterType)) return baseAttack - 2;
        return baseAttack;
    }
    @Override
    public int modifyDefense(String characterType, int baseDefense) {
        if ("orc".equals(characterType)) return baseDefense + 2;
        return baseDefense;
    }
}
