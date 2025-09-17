package com.oop.examples.patterns.dnd;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TerrainFactory {
    private static final List<Terrain> TERRAINS = Arrays.asList(
        new Mountain(), new Forest(), new Swamp(), new Desert()
    );
    private static final Random random = new Random();

    public static Terrain randomTerrain() {
        return TERRAINS.get(random.nextInt(TERRAINS.size()));
    }
}
