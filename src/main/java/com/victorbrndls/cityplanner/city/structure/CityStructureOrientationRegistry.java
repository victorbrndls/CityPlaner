package com.victorbrndls.cityplanner.city.structure;

import com.victorbrndls.cityplanner.city.structure.industry.LumberMillCityStructureOrientation;
import com.victorbrndls.cityplanner.city.structure.industry.VegetableFarmCityStructureOrientation;
import com.victorbrndls.cityplanner.city.structure.industry.WaterWellCityStructureOrientation;
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
            }
            case WIND_MILL -> {
            }
            case FURNITURE_FACTORY -> {
            }
        }

        throw new UnsupportedOperationException("missing orientation for " + structure);
    }

}
