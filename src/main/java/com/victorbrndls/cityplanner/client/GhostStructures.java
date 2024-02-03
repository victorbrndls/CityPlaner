package com.victorbrndls.cityplanner.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.victorbrndls.cityplanner.city.structure.CityStructure;
import com.victorbrndls.cityplanner.city.structure.CityStructureOrientation;
import com.victorbrndls.cityplanner.city.structure.LumberMillCityStructureOrientation;
import com.victorbrndls.cityplanner.item.CityPlannerItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.data.ModelData;
import org.joml.Quaternionf;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GhostStructures {

    private final List<CityStructure> ghosts = new ArrayList<>();

    public void add(CityStructure entry) {
        ghosts.add(entry);
    }

    public void tick() {
        Item mainHandItem = Minecraft.getInstance().player.getMainHandItem().getItem();
        var isStructure = mainHandItem == CityPlannerItems.LUMBER_MILL_ITEM.get();

        if (!isStructure) {
            ghosts.clear();
            return;
        }

        if (ghosts.contains(CityStructure.LUMBER_MILL)) return;
        ghosts.add(CityStructure.LUMBER_MILL);
    }

    public void renderAll(PoseStack ms, MultiBufferSource buffer, Vec3 camera) {
        ghosts.forEach(structure -> render(ms, buffer, camera, structure));
    }

    private void render(PoseStack ms, MultiBufferSource buffer, Vec3 camera, CityStructure structure) {
        var structureInfo = CityPlannerClient.STRUCTURE_CACHE.get(structure);
        if (structureInfo == null) return;

        var blocks = structureInfo.pos();
        var states = structureInfo.blockState();

        var direction = Minecraft.getInstance().player.getDirection();
        var orientation = new LumberMillCityStructureOrientation(direction);

        for (int i = 0; i < blocks.size(); i++) {
            render(ms, buffer, camera, blocks.get(i), states.get(i), orientation);
        }
    }

    private void render(
            PoseStack ms,
            MultiBufferSource buffer,
            Vec3 camera,
            BlockPos blockPos,
            BlockState blockState,
            CityStructureOrientation orientation
    ) {
        BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();

        HitResult hitResult = Minecraft.getInstance().hitResult;
        if (hitResult == null) return;

        Vec3 structureCenter = orientation.getCenter();
        Vec3 blockPosVec = new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());

        BakedModel model = dispatcher.getBlockModel(blockState);

        ms.pushPose();

        orientation.translate(ms);

        var offsetFromCenter = blockPosVec.subtract(structureCenter);
        var angleRadians = (float) Math.toRadians(orientation.rotationDegrees());
        var blockPosAfterRotation = offsetFromCenter.yRot(angleRadians);

        Vec3 blockPosInWorld = hitResult.getLocation().add(
                blockPosAfterRotation.x, blockPosAfterRotation.y, blockPosAfterRotation.z
        );

        ms.translate(
                blockPosInWorld.x() - camera.x, blockPosInWorld.y() - camera.y, blockPosInWorld.z() - camera.z
        );

        var rotationQuaternion = new Quaternionf(0, (float) Math.sin(angleRadians / 2), 0, (float) Math.cos(angleRadians / 2));
        ms.mulPose(rotationQuaternion);

        VertexConsumer vb = buffer.getBuffer(RenderType.translucent());
        renderModel(
                ms.last(),
                vb,
                blockState,
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
