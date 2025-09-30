package com.oop.examples.patterns.dnd.factory;

import com.oop.examples.patterns.dnd.Armour;
import com.oop.examples.patterns.dnd.Weapon;

/**
 * Factory interface for creating game equipment.
 */
public interface EquipmentFactory {
    Weapon createRandomWeapon();
    Armour createRandomArmour();
}
