package com.victorbrndls.cityplanner.city.structure;

import net.minecraft.core.Direction;

public class CityStructureOrientationRegistry {

    public CityStructureOrientation getOrientation(CityStructure structure, Direction direction) {
        switch (structure) {
            case LUMBER_MILL -> {
                return new LumberMillCityStructureOrientation(direction);
            }
            case VEGETABLE_FARM -> {
                return new VegetableFarmCityStructureOrientation(direction);
            }
            case WATER_WELL -> {
            }
            case RESIDENCE_1 -> {
            }
            case WIND_MILL -> {
            }
            case FURNITURE_FACTORY -> {
            }
        }

        return new LumberMillCityStructureOrientation(direction); // TODO: remove
    }

}
