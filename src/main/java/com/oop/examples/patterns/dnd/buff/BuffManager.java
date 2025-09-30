package com.oop.examples.patterns.dnd.buff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oop.examples.patterns.dnd.Equipment;
import com.oop.examples.patterns.dnd.Hero;
import com.oop.examples.patterns.dnd.Enemy;
import com.oop.examples.patterns.dnd.buff.Buff;

/**
 * Centralized buff management system.
 * Uses a facade pattern to simplify buff application across the codebase.
 */
public static class BuffManager {
    private static final Map<Class<?>, List<Buff>> BUFFS = new HashMap<>();

    /**
     * Registers a buff for a specific target class.
     * Multiple buffs can be registered for the same class,
     * and they will be applied in the order they were registered.
     *
     * @param targetClass the class to register the buff for
     * @param buff the buff to be registered
     */
    public static void addBuff(Class<?> targetClass, Buff buff) {
        BUFFS.computeIfAbsent(targetClass, k -> new ArrayList<>()).add(buff);
    }

    public static int applyBuffs(Object target, int baseValue, String effectType) {
        int result = baseValue;
        // Apply class-based buffs
        result = applyBuffsForClass(target.getClass(), result, effectType);
        // Apply instance-specific buffs if available
        if (target instanceof Equipment equipment) {
        result = applyEquipmentBuffs(equipment, result, effectType);
        }
        return result;
    }

    private static int applyBuffsForClass(Class<?> clazz, int value, String effectType) {
        return BUFFS.getOrDefault(clazz, List.of()).stream()
            .filter(b -> b.effectType().equals(effectType))
            .reduce(value, (val, buff) -> buff.apply(val), Integer::sum);
    }

    private static int applyEquipmentBuffs(Equipment equipment, int value, String effectType) {
        // Equipment-specific buff logic can be added here
        return value;
    }
}