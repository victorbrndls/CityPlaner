package com.victorbrndls.cityplanner.gui;

import com.victorbrndls.cityplanner.network.CityStatsMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;

public class CityStatsRenderer {

    public static CityStatsMessage message = null;

    public static void render(GuiGraphics ctx, float delta) {
        var currentMessage = message;
        if (currentMessage == null) return;

        var client = Minecraft.getInstance();

        var width = client.getWindow().getGuiScaledWidth();

        int startX = (int) (width * 0.2);
        int endX = (int) (width * 0.8);
        int startY = 20;

        var strings = new ArrayList<String>();
        strings.add("planks: " + message.plankAmount());
        strings.add("vegetables: " + message.vegetableAmount());
        strings.add("population: " + message.population());

        var lastX = startX;
        var lastY = startY;

        for (String string : strings) {
            ctx.drawString(client.font, string, lastX, lastY, 0xFFFFFF);

            var stringWidth = client.font.width(string);
            var horizontalPadding = 12;
            var stringHeight = client.font.lineHeight;

            lastX = lastX + stringWidth + horizontalPadding;

            if (lastY > endX) {
                lastX = startX;
                lastY = lastY + stringHeight;
            }
        }
    }
}