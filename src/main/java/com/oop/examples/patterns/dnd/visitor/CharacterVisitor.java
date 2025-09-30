package com.oop.examples.patterns.dnd.visitor;

import com.oop.examples.patterns.dnd.character.Hero;
import com.oop.examples.patterns.dnd.character.Enemy;

/**
 * Visitor interface for game characters.
 */
public interface CharacterVisitor {
  int visit(Hero hero);
  int visit(Enemy enemy);
}