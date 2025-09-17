package com.oop.examples.patterns.dnd;

public interface Terrain {
    String getName();
    int modifyAttack(String characterType, int baseAttack);
    int modifyDefense(String characterType, int baseDefense);
}
