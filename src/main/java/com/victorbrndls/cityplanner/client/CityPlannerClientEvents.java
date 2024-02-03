package com.victorbrndls.cityplanner.client;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.victorbrndls.cityplanner.gui.CityStatsRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class CityPlannerClientEvents {

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        if (!isGameActive())
            return;

        //Level world = Minecraft.getInstance().level;
        CityPlannerClient.GHOST_STRUCTURES.tick();
    }

    @SubscribeEvent
    public static void guiRendered(final RenderGuiEvent.Post event) {
        CityStatsRenderer.render(event.getGuiGraphics(), event.getPartialTick());
    }

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES)
            return;

        PoseStack ms = event.getPoseStack();
        ms.pushPose();

        var buffer = MultiBufferSource.immediate(new BufferBuilder(1536));
        Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        var direction = Minecraft.getInstance().player.getDirection();

        CityPlannerClient.GHOST_STRUCTURES.renderAll(ms, buffer, camera, direction);

        buffer.endLastBatch();
        ms.popPose();
    }

    private static boolean isGameActive() {
        return !(Minecraft.getInstance().level == null || Minecraft.getInstance().player == null);
    }

}