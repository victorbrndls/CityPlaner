package com.victorbrndls.cityplanner.client;

import com.victorbrndls.cityplanner.city.structure.CityStructureOrientationRegistry;

public class CityPlannerClient {

    public static ClientStructureCache STRUCTURE_CACHE = new ClientStructureCache();
    public static CityStructureOrientationRegistry STRUCTURE_ORIENTATION_REGISTRY = new CityStructureOrientationRegistry();

    public static GhostStructures GHOST_STRUCTURES = new GhostStructures();

}
