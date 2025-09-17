package com.oop.examples.patterns.dnd;

public class Mountain implements Terrain {
    @Override
    public String getName() { return "Montaña"; }
    @Override
    public int modifyAttack(String characterType, int baseAttack) {
        if ("dwarf".equals(characterType)) return baseAttack + 4;
        if ("elf".equals(characterType)) return baseAttack - 2;
        return baseAttack;
    }
    @Override
    public int modifyDefense(String characterType, int baseDefense) {
        if ("dwarf".equals(characterType)) return baseDefense + 2;
        return baseDefense;
    }
}
