package com.victorbrndls.cityplanner.gui.screen;

import com.victorbrndls.cityplanner.gui.CityPlannerColors;
import com.victorbrndls.cityplanner.gui.CityPlannerGuiResources;
import com.victorbrndls.cityplanner.network.milestone.Level1MilestoneMessage;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class Level1MilestoneRendererImpl implements MilestoneRenderer {

    private final Level1MilestoneMessage message;

    public Level1MilestoneRendererImpl(Level1MilestoneMessage message) {
        this.message = message;
    }

    @Override
    public void render(GuiGraphics ctx, Font font, int width, int height, int i, int j) {
        var startX = i + CityFoundationScreen.WINDOW_INSIDE_X + 4;
        var startY = j + CityFoundationScreen.WINDOW_INSIDE_Y + 4;

        ctx.drawString(font, "Current Milestone: Level 1", startX, startY, CityPlannerColors.WHITE);

        ctx.blit(
                CityPlannerGuiResources.RESIDENT_RESOURCE,
                startX, startY + 12,
                0, 0,
                12, 12,
                12, 12
        );

        var population = message.currentPopulation() + "/" + message.populationRequired();
        ctx.drawString(font, "Population: " + population, startX + 16, startY + 15, CityPlannerColors.WHITE);
    }

}
