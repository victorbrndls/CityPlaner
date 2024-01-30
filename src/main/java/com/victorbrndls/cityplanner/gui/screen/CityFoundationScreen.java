package com.victorbrndls.cityplanner.gui.screen;

import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CityFoundationScreen extends Screen {

    public static MilestoneRenderer renderer = null;

    public CityFoundationScreen() {
        super(GameNarrator.NO_TITLE);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        if (renderer != null) {
            renderer.render(pGuiGraphics);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
