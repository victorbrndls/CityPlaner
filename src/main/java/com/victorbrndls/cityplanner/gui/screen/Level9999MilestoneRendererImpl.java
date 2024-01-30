package com.victorbrndls.cityplanner.gui.screen;

import com.victorbrndls.cityplanner.network.Level9999MilestoneMessage;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class Level9999MilestoneRendererImpl implements MilestoneRenderer {

    private final Level9999MilestoneMessage message;

    public Level9999MilestoneRendererImpl(Level9999MilestoneMessage message) {
        this.message = message;
    }

    @Override
    public void render(GuiGraphics ctx, Font font, int width, int height, int i, int j) {

    }

}
