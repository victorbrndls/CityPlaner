package com.victorbrndls.cityplanner.city;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.network.CityStatsMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class CitiesController {

    private int currentTick = 0;

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

    @Nullable
    public City getCityInRange(BlockPos pos) {
        for (var city : cities) {
            if (isCityInRange(city, pos)) {
                return city;
            }
        }
        return null;
    }

    public boolean hasCityInRange(BlockPos pos) {
        return cities.stream().anyMatch(city -> isCityInRange(city, pos));
    }

    private static boolean isCityInRange(City city, BlockPos pos) {
        double xDiff = Math.abs(city.getPosition().getX() - pos.getX());
        double zDiff = Math.abs(city.getPosition().getZ() - pos.getZ());

        return xDiff < 50 && zDiff < 50;
    }

    public void tick(Level level) {
        currentTick++;
        if (cities.size() == 0) return;

        cities.forEach(City::tick);

        if (currentTick == 40) {
            currentTick = 0;

            sendCityStatsToClient(level);
        }
    }

    private void sendCityStatsToClient(Level level) {
        for (var player : level.players()) {
            var city = getCityInRange(player.getOnPos());
            if (city == null) continue;

            CityPlannerMod.CHANNEL.send(CityStatsMessage.create(city), PacketDistributor.PLAYER.with((ServerPlayer) player));

            // TODO: improve this
            CityPlannerMod.CHANNEL.send(city.getCurrentMilestone().getMessage(), PacketDistributor.PLAYER.with((ServerPlayer) player));
        }
    }

}
