package com.victorbrndls.cityplanner.city.milestone;

import com.victorbrndls.cityplanner.city.City;
import com.victorbrndls.cityplanner.gui.MilestoneRenderer;
import net.minecraft.client.gui.GuiGraphics;

public class Level1Milestone implements Milestone, MilestoneRenderer {

    private final City city;

    public Level1Milestone(City city) {
        this.city = city;
    }

    @Override
    public double progress() {
        return city.getResidentCount() / 10.0;
    }

    @Override
    public void render(GuiGraphics ctx) {

    }
}
