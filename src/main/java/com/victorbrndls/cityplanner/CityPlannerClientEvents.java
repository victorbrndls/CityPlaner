package com.victorbrndls.cityplanner;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
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

        CityPlannerClient.GHOST_BLOCKS.tick();
    }

    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES)
            return;

        PoseStack ms = event.getPoseStack();
        ms.pushPose();

        var buffer = MultiBufferSource.immediate(new BufferBuilder(1536));
        Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();

        CityPlannerClient.GHOST_BLOCKS.renderAll(ms, buffer, camera);

        buffer.endLastBatch();
        ms.popPose();
    }

    private static boolean isGameActive() {
        return !(Minecraft.getInstance().level == null || Minecraft.getInstance().player == null);
    }

}