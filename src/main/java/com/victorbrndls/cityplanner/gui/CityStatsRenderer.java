package com.victorbrndls.cityplanner.gui;

import com.victorbrndls.cityplanner.network.CityStatsMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class CityStatsRenderer {

    private static final ResourceLocation PLANK_RESOURCE =
            new ResourceLocation("cityplanner", "textures/gui/resource_plank.png");
    private static final ResourceLocation WATER_RESOURCE =
            new ResourceLocation("cityplanner", "textures/gui/resource_water.png");
    private static final ResourceLocation VEGETABLE_RESOURCE =
            new ResourceLocation("cityplanner", "textures/gui/resource_vegetable.png");
    private static final ResourceLocation RESIDENT_RESOURCE =
            new ResourceLocation("cityplanner", "textures/gui/resource_resident.png");

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

        ctx.blit(PLANK_RESOURCE, lastX, startY, 0, 0, 12, 12, 12, 12);
        ctx.drawString(client.font, String.valueOf(message.plankAmount()), lastX + 14, lastY + 4, 0xFFFFFF);
        lastX = lastX + 14 + stringWidth;

        ctx.blit(VEGETABLE_RESOURCE, lastX, startY, 0, 0, 12, 12, 12, 12);
        ctx.drawString(client.font, String.valueOf(message.vegetableAmount()), lastX + 12, lastY + 4, 0xFFFFFF);
        lastX = lastX + 12 + stringWidth;

        ctx.blit(WATER_RESOURCE, lastX, startY, 0, 0, 12, 12, 12, 12);
        ctx.drawString(client.font, String.valueOf(message.waterAmount()), lastX + 12, lastY + 4, 0xFFFFFF);
        lastX = lastX + 12 + stringWidth;

        ctx.blit(RESIDENT_RESOURCE, startX, startY + 18, 0, 0, 12, 12, 12, 12);
        ctx.drawString(client.font, String.valueOf(message.population()), startX + 14, startY + 18 + 4, 0xFFFFFF);
        lastX = startX;
        lastY = lastY + 18 + 4;
    }
}