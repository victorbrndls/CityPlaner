package com.victorbrndls.cityplanner.gui;

import com.victorbrndls.cityplanner.network.CityStatsMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class CityStatsRenderer {

    public static CityStatsMessage message = null;

    public static void render(GuiGraphics ctx, float delta) {
        var currentMessage = message;
        if (currentMessage == null) return;

        var client = Minecraft.getInstance();

        var width = client.getWindow().getGuiScaledWidth();
        var height = client.getWindow().getGuiScaledHeight();

        ctx.drawString(client.font, "planks: " + message.plankAmount(), width / 2, 20, 0xFFFFFF);
        ctx.drawString(client.font, "vegetables: " + message.vegetableAmount(), width / 2, 29, 0xFFFFFF);
        ctx.drawString(client.font, "population: " + message.population(), width / 2, 38, 0xFFFFFF);
    }
}