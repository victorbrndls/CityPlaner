package com.victorbrndls.cityplanner.network;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.city.CitiesController;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NetworkListener {

    @SubscribeEvent
    public void onLevelTick(TickEvent.LevelTickEvent e) {
        if (!e.level.isClientSide() && e.phase == TickEvent.Phase.END) {
            CitiesController controller = CityPlannerMod.citiesController;
            if (controller == null) return;

            controller.tick();
        }
    }
}