package com.victorbrndls.cityplanner.gui.screen;

import com.victorbrndls.cityplanner.gui.CityPlannerColors;
import com.victorbrndls.cityplanner.gui.CityPlannerGuiResources;
import com.victorbrndls.cityplanner.network.Level2MilestoneMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class Level2MilestoneRendererImpl implements MilestoneRenderer {

    private final Level2MilestoneMessage message;

    public Level2MilestoneRendererImpl(Level2MilestoneMessage message) {
        this.message = message;
    }

    @Override
    public void render(GuiGraphics ctx, Font font, int width, int height, int i, int j) {
        var startX = i + CityFoundationScreen.WINDOW_INSIDE_X + 4;
        var startY = j + CityFoundationScreen.WINDOW_INSIDE_Y + 4;

        ctx.drawString(font, "Current Milestone: Level 2", startX, startY, CityPlannerColors.WHITE);

        ctx.blit(
                CityPlannerGuiResources.RESIDENT_RESOURCE,
                startX, startY + 12,
                0, 0,
                12, 12,
                12, 12
        );

        var population = message.currentPopulation() + "/" + message.populationRequired();
        ctx.drawString(font, "Population: " + population, startX + 16, startY + 15, CityPlannerColors.WHITE);

        ctx.blit(
                CityPlannerGuiResources.RESIDENT_SATISFACTION,
                startX, startY + 12 + 16,
                0, 0,
                12, 12,
                12, 12
        );

        var satisfaction = message.currentSatisfaction() + "/" + message.satisfactionRequired();
        ctx.drawString(font, "Satisfaction: " + satisfaction, startX + 16, startY + 15 + 16, CityPlannerColors.WHITE);
    }

}
