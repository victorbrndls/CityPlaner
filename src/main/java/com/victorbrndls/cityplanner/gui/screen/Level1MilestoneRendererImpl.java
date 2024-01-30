package com.victorbrndls.cityplanner.gui.screen;

import com.victorbrndls.cityplanner.network.Level1MilestoneMessage;
import net.minecraft.client.gui.GuiGraphics;

public class Level1MilestoneRendererImpl implements MilestoneRenderer {

    private final Level1MilestoneMessage message;

    public Level1MilestoneRendererImpl(Level1MilestoneMessage message) {
        this.message = message;
    }

    @Override
    public void render(GuiGraphics ctx) {

    }

}
