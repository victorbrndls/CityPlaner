package com.victorbrndls.cityplanner.proxy;

import com.victorbrndls.cityplanner.gui.screen.CityFoundationScreen;
import net.minecraft.client.Minecraft;

public class CityPlannerClientProxy implements CityPlannerProxy {

    @Override
    public void openCityFoundationScreen() {
        Minecraft.getInstance().setScreen(new CityFoundationScreen());
    }
}
