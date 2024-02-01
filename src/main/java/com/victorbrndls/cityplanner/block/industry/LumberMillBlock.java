package com.victorbrndls.cityplanner.block.industry;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.helper.CityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class LumberMillBlock extends Block implements EntityBlock {

    public LumberMillBlock() {
        super(Blocks.SPRUCE_WOOD.properties()
                .pushReaction(PushReaction.BLOCK)
        );
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);

        if (pLevel.isClientSide) return;
        if (CityPlannerMod.citiesController.hasCityInRange(pPos)) {
            placeStructure(pLevel, pPos, pPlacer);
            return;
        }

        pLevel.destroyBlock(pPos, true);
        CityHelper.displayMessageToNearbyPlayers(pLevel, pPos, "No city in range");
    }

    private void placeStructure(Level level, BlockPos pos, LivingEntity entity) {
        var serverLevel = (ServerLevel) level;
        var structureManager = serverLevel.getStructureManager();

        Optional<StructureTemplate> structureTemplate = structureManager.get(new ResourceLocation("cityplanner", "lumber_mill"));

        structureTemplate.ifPresent((struct) -> {
            StructurePlaceSettings settings = new StructurePlaceSettings();
            var xOffset = 0;
            var zOffset = 0;

            switch (entity.getDirection()) {
                case NORTH -> {
                    settings.setRotation(Rotation.CLOCKWISE_180);
                    xOffset = 2;
                }
                case SOUTH -> {
                    settings.setRotation(Rotation.NONE);
                    xOffset = -2;
                }
                case WEST -> {
                    settings.setRotation(Rotation.CLOCKWISE_90);
                    zOffset = -2;
                }
                case EAST -> {
                    settings.setRotation(Rotation.COUNTERCLOCKWISE_90);
                    zOffset = 2;
                }
                default -> {
                }
            }

            StructureTemplate.Palette palette = struct.palettes.get(0);
            palette.blocks().removeIf(blockInfo -> blockInfo.state().isAir());

            struct.placeInWorld(
                    serverLevel, pos.offset(xOffset, 0, zOffset), pos, settings, null, 2
            );
        });
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new LumberMillBlockEntity(pPos, pState);
    }
}
