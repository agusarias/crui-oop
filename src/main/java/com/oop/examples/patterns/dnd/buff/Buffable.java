package com.oop.examples.patterns.dnd.buff;

/**
 * Interface for any entity that can have buffs applied to it.
 * This allows for flexible buff application across different game elements.
 */
public interface Buffable {
  default int applyBuffs(int baseValue, String effectType) {
    return BuffManager.applyBuffs(this, baseValue, effectType);
  }
}