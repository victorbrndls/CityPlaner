package com.victorbrndls.cityplanner;

import com.victorbrndls.cityplanner.gui.CityStatsRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CityPlannerMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void guiRendered(final RenderGuiEvent.Post event) {
        CityStatsRenderer.render(event.getGuiGraphics(), event.getPartialTick());
    }
}
