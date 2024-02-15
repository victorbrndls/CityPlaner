package com.victorbrndls.cityplanner.city.structure;

import com.victorbrndls.cityplanner.city.structure.industry.*;
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
                return new WaterWellCityStructureOrientation(direction);
            }
            case RESIDENCE_1 -> {
                return new ResidenceLevel1CityStructureOrientation(direction);
            }
            case WIND_TURBINE -> {
                return new WindTurbineCityStructureOrientation(direction);
            }
            case FURNITURE_FACTORY -> {
                return new FurnitureFactoryCityStructureOrientation(direction);
            }
        }

        throw new UnsupportedOperationException("missing orientation for " + structure);
    }

}
