package com.victorbrndls.cityplanner.client;

import com.victorbrndls.cityplanner.city.structure.CityStructure;
import com.victorbrndls.cityplanner.city.structure.CityStructureInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientStructureCache {

    private final Map<CityStructure, CityStructureInfo> STRUCTURE_CACHE = new HashMap<>();
    private final List<CityStructure> REQUESTS_IN_PROGRESS = new ArrayList<>();

    public void add(CityStructureInfo info) {
        STRUCTURE_CACHE.put(info.structure(), info);
    }

    public CityStructureInfo get(CityStructure structure) {
        return STRUCTURE_CACHE.get(structure);
    }

    public void requestMade(CityStructure structure) {
        REQUESTS_IN_PROGRESS.add(structure);
    }

    public boolean shouldRequest(CityStructure structure) {
        return !STRUCTURE_CACHE.containsKey(structure) && !REQUESTS_IN_PROGRESS.contains(structure);
    }
}
