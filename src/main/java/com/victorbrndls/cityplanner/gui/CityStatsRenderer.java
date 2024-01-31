package com.victorbrndls.cityplanner.gui;

import com.victorbrndls.cityplanner.network.CityStatsMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class CityStatsRenderer {

    public static CityStatsMessage message = null;

    public static void render(GuiGraphics ctx, float delta) {
        var currentMessage = message;
        if (currentMessage == null) return;

        var client = Minecraft.getInstance();

        var width = client.getWindow().getGuiScaledWidth();

        int startX = (int) (width * 0.2);
        int startY = 20;

        var lastX = startX;
        var lastY = startY;

        var stringWidth = client.font.width("0000"); // up to 4 numbers

        var topStats = getFirstBoxStats();
        var bottomStats = getSecondBoxStats();

        for (var stat : topStats) {
            ctx.blit(stat.resource(), lastX, lastY, 0, 0, 12, 12, 12, 12);
            ctx.drawString(client.font, stat.text(), lastX + 14, lastY + 4, 0xFFFFFF);
            lastX = lastX + 12 + stringWidth;
        }

        lastX = startX;
        lastY = startY + 18;
        for (var stat : bottomStats) {
            ctx.blit(stat.resource(), lastX, lastY, 0, 0, 12, 12, 12, 12);
            ctx.drawString(client.font, stat.text(), lastX + 14, lastY + 4, 0xFFFFFF);
            lastX = lastX + 12 + stringWidth;
        }
    }

    private static List<Stat> getFirstBoxStats() {
        return List.of(
                new Stat(CityPlannerGuiResources.WATER_RESOURCE, String.valueOf(message.waterAmount())),
                new Stat(CityPlannerGuiResources.ENERGY_RESOURCE, String.valueOf(message.energyAmount())),
                new Stat(CityPlannerGuiResources.PLANK_RESOURCE, String.valueOf(message.plankAmount())),
                new Stat(CityPlannerGuiResources.VEGETABLE_RESOURCE, String.valueOf(message.vegetableAmount())),
                new Stat(CityPlannerGuiResources.FURNITURE_RESOURCE, String.valueOf(message.furnitureAmount()))
        );
    }

    private static List<Stat> getSecondBoxStats() {
        return List.of(
                new Stat(CityPlannerGuiResources.RESIDENT_RESOURCE, String.valueOf(message.population())),
                new Stat(CityPlannerGuiResources.RESIDENT_SATISFACTION, message.satisfaction() + "%")
        );
    }

    private record Stat(ResourceLocation resource, String text) {
    }
}