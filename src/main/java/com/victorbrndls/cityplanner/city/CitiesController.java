package com.victorbrndls.cityplanner.city;

import com.victorbrndls.cityplanner.CityPlannerMod;
import net.minecraft.core.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class CitiesController {

    public Set<City> cities = new HashSet<>();

    public void addCity(City city) {
        synchronized (this) {
            cities.add(city);
            CityPlannerMod.LOGGER.info("+ Added city #" + city.getId());
        }
    }

    public void removeCity(BlockPos pos) {
        synchronized (this) {
            cities.stream().filter(city ->
                    city.getPosition().equals(pos)
            ).findFirst().ifPresent((city) -> {
                cities.remove(city);
                CityPlannerMod.LOGGER.info("- Removed city #" + city.getId());
            });
        }
    }

    public void removeCity(City city) {
        synchronized (this) {
            cities.remove(city);
            CityPlannerMod.LOGGER.info("- Removed city #" + city.getId());
        }
    }

    public void tick() {
        cities.forEach(City::tick);
    }

}
