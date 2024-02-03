package com.victorbrndls.cityplanner;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.data.ModelData;

import javax.annotation.Nullable;
import java.util.List;

public class GhostBlocks {

    public void tick() {

    }

    public void renderAll(PoseStack ms, MultiBufferSource buffer, Vec3 camera) {
        ms.pushPose();
        BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();

        HitResult hitResult = Minecraft.getInstance().hitResult;
        if (hitResult == null) return;

        BlockState state = Blocks.IRON_BLOCK.defaultBlockState();
        Vec3 pos = hitResult.getLocation().add(0, 0, 0);

        BakedModel model = dispatcher.getBlockModel(state);

        ms.pushPose();
        ms.translate(pos.x() - camera.x, pos.y() - camera.y, pos.z() - camera.z);

        VertexConsumer vb = buffer.getBuffer(RenderType.translucent());
        renderModel(
                ms.last(),
                vb,
                state,
                model,
                1f,
                1f,
                1f,
                0.7f,
                LightTexture.FULL_BRIGHT,
                OverlayTexture.NO_OVERLAY,
                ModelData.EMPTY,
                RenderType.translucent()
        );

        ms.popPose();
    }

    public void renderModel(PoseStack.Pose pose, VertexConsumer consumer,
                            @Nullable BlockState state, BakedModel model, float red, float green, float blue,
                            float alpha, int packedLight, int packedOverlay, ModelData modelData, RenderType renderType) {
        RandomSource random = RandomSource.create();

        for (Direction direction : Direction.values()) {
            random.setSeed(42L);
            renderQuadList(pose, consumer, red, green, blue, alpha,
                    model.getQuads(state, direction, random, modelData, null), packedLight, packedOverlay);
        }

        random.setSeed(42L);
        renderQuadList(pose, consumer, red, green, blue, alpha,
                model.getQuads(state, null, random, modelData, null), packedLight, packedOverlay);
    }

    // ModelBlockRenderer
    private static void renderQuadList(PoseStack.Pose pose, VertexConsumer consumer,
                                       float red, float green, float blue, float alpha, List<BakedQuad> quads,
                                       int packedLight, int packedOverlay) {
        for (BakedQuad quad : quads) {
            float f;
            float f1;
            float f2;
            if (quad.isTinted()) {
                f = Mth.clamp(red, 0.0F, 1.0F);
                f1 = Mth.clamp(green, 0.0F, 1.0F);
                f2 = Mth.clamp(blue, 0.0F, 1.0F);
            } else {
                f = 1.0F;
                f1 = 1.0F;
                f2 = 1.0F;
            }

            consumer.putBulkData(pose, quad, f, f1, f2, alpha, packedLight, packedOverlay, true);
        }

    }
}
