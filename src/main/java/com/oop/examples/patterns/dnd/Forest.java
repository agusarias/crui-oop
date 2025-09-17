package com.oop.examples.patterns.dnd;

public class Forest implements Terrain {
    @Override
    public String getName() { return "Bosque"; }
    @Override
    public int modifyAttack(String characterType, int baseAttack) {
        if ("elf".equals(characterType)) return baseAttack + 4;
        if ("orc".equals(characterType)) return baseAttack - 2;
        return baseAttack;
    }
    @Override
    public int modifyDefense(String characterType, int baseDefense) {
        if ("elf".equals(characterType)) return baseDefense + 2;
        return baseDefense;
    }
}
