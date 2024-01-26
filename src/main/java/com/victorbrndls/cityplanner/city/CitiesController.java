package com.victorbrndls.cityplanner.city;

import com.victorbrndls.cityplanner.CityPlannerMod;
import net.minecraft.core.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class CitiesController {

    private Set<City> cities = new HashSet<>();

    public void addCity(City city) {
        synchronized (this) {
            cities.add(city);
            CityPlannerMod.LOGGER.info("+ Added city #" + city.getId());
        }
    }

    public void removeCity(City city) {
        synchronized (this) {
            cities.remove(city);
            CityPlannerMod.LOGGER.info("- Removed city #" + city.getId());
        }
    }

    public City getCityInRange(BlockPos pos) {
        return cities.stream().filter(city ->
                city.getPosition().distToCenterSqr(pos.getX(), pos.getY(), pos.getZ()) < 10
        ).findFirst().orElse(null);
    }

    public void tick() {
        cities.forEach(City::tick);
    }

}
