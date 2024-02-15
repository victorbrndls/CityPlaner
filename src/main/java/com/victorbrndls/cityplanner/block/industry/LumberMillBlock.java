package com.victorbrndls.cityplanner.block.industry;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.city.structure.industry.LumberMillCityStructureOrientation;
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
            var cityStructureOrientation = new LumberMillCityStructureOrientation(entity.getDirection());

            cityStructureOrientation.updateSettings(settings);
            var offset = cityStructureOrientation.getOffset();

            StructureTemplate.Palette palette = struct.palettes.get(0);
            palette.blocks().removeIf(blockInfo -> blockInfo.state().isAir());

            struct.placeInWorld(
                    serverLevel,
                    pos.offset((int) offset.x, (int) offset.y, (int) offset.z),
                    pos,
                    settings,
                    null,
                    2
            );
        });
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new LumberMillBlockEntity(pPos, pState);
    }
}
