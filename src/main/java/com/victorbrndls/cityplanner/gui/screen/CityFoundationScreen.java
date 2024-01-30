package com.victorbrndls.cityplanner.gui.screen;

import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

public class CityFoundationScreen extends Screen {

    private static final ResourceLocation WINDOW_LOCATION =
            new ResourceLocation("cityplanner", "textures/gui/backgrounds/city_foundation_window.png");

    public static final int WINDOW_WIDTH = 252;
    public static final int WINDOW_HEIGHT = 140;

    public static final int WINDOW_INSIDE_X = 9;
    public static final int WINDOW_INSIDE_Y = 18;

    public static final int WINDOW_INSIDE_WIDTH = 234;
    public static final int WINDOW_INSIDE_HEIGHT = 113;

    public static MilestoneRenderer renderer = null;

    public CityFoundationScreen() {
        super(GameNarrator.NO_TITLE);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        int i = (width - WINDOW_WIDTH) / 2;
        int j = (height - WINDOW_HEIGHT) / 2;

        pGuiGraphics.blit(WINDOW_LOCATION, i, j, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        if (renderer != null) {
            renderer.render(pGuiGraphics, font, width, height, i, j);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
