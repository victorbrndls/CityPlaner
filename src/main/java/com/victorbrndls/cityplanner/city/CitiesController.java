package com.victorbrndls.cityplanner.city;

import com.victorbrndls.cityplanner.CityPlannerMod;
import net.minecraft.core.BlockPos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class CitiesController {

    private final Set<City> cities = new HashSet<>();

    private final List<BlockPosAndConsumer> toExecute = new ArrayList<>();

    public void addCity(City city) {
        synchronized (this) {
            cities.add(city);
            CityPlannerMod.LOGGER.info("+ Added city #" + city.getId());

            toExecute.removeIf(posAndConsumer -> {
                if (isCityInRange(city, posAndConsumer.pos())) {
                    posAndConsumer.onCityFound().accept(city);
                    return true;
                }
                return false;
            });
        }
    }

    public void removeCity(City city) {
        synchronized (this) {
            cities.remove(city);
            CityPlannerMod.LOGGER.info("- Removed city #" + city.getId());
        }
    }

    public void getCityInRange(BlockPos pos, Consumer<City> onCityFound) {
        City closestCity = cities.stream().filter(city -> isCityInRange(city, pos)).findFirst().orElse(null);

        if (closestCity != null) {
            onCityFound.accept(closestCity);
        } else {
            toExecute.add(new BlockPosAndConsumer(pos, onCityFound));
        }
    }

    private static boolean isCityInRange(City city, BlockPos pos) {
        double xDiff = Math.abs(city.getPosition().getX() - pos.getX());
        double zDiff = Math.abs(city.getPosition().getZ() - pos.getZ());

        return xDiff < 250 && zDiff < 250;
    }

    public void tick() {
        cities.forEach(City::tick);
    }

}
