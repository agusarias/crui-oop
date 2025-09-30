package com.oop.examples.patterns.dnd.factory;

import com.oop.examples.patterns.dnd.character.Character;

/**
 * Factory interface for creating game characters.
 */
public interface CharacterFactory {
    Character createRandomHero();
    Character createRandomEnemy();
    
    default Character createRandomCharacter(boolean isHero) {
        return isHero ? createRandomHero() : createRandomEnemy();
    }
}