package com.victorbrndls.cityplanner.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class CityStatsRenderer {

    public static void render(GuiGraphics ctx, float delta) {
        var client = Minecraft.getInstance();

        var width = client.getWindow().getGuiScaledWidth();
        var height = client.getWindow().getGuiScaledHeight();

        ctx.drawString(client.font, "City Planner", width / 2, height / 2, 0xFFFFFF);
    }
}