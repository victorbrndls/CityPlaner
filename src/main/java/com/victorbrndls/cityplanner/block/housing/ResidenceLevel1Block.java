package com.victorbrndls.cityplanner.block.housing;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.city.City;
import com.victorbrndls.cityplanner.city.Resource;
import com.victorbrndls.cityplanner.city.structure.industry.ResidenceLevel1CityStructureOrientation;
import com.victorbrndls.cityplanner.helper.CityHelper;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
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

public class ResidenceLevel1Block extends Block implements EntityBlock {

    public ResidenceLevel1Block() {
        super(Blocks.SPRUCE_WOOD.properties()
                .pushReaction(PushReaction.BLOCK)
        );
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);

        if (pLevel.isClientSide) return;
        City city = CityPlannerMod.citiesController.getCityInRange(pPos);

        if (city != null) {
            var hasResources = city.tryConsume(Resource.PLANK, 10);
            if (hasResources) {
                placeStructure(pLevel, pPos, pPlacer);
            } else {
                pLevel.destroyBlock(pPos, true);
                CityHelper.displayMessageToNearbyPlayers(pLevel, pPos, "You need 10 planks to build a residence");
            }
        } else {
            pLevel.destroyBlock(pPos, true);
            CityHelper.displayMessageToNearbyPlayers(pLevel, pPos, "No city in range");
        }
    }

    private void placeStructure(Level level, BlockPos pos, LivingEntity entity) {
        var serverLevel = (ServerLevel) level;
        var structureManager = serverLevel.getStructureManager();

        Optional<StructureTemplate> structureTemplate = structureManager.get(new ResourceLocation("cityplanner", "house_1"));
        structureTemplate.ifPresent((struct) -> {
            StructurePlaceSettings settings = new StructurePlaceSettings();
            var cityStructureOrientation = new ResidenceLevel1CityStructureOrientation(entity.getDirection());

            cityStructureOrientation.updateSettings(settings);
            var offset = cityStructureOrientation.getOffset();

            StructureTemplate.Palette palette = struct.palettes.get(0);
            palette.blocks().removeIf(blockInfo -> blockInfo.state().isAir());

            struct.placeInWorld(
                    serverLevel,
                    pos.offset((int) offset.x, (int) offset.y, (int) offset.z),
                    pos,
                    settings,
                    RandomSource.create(Util.getMillis()),
                    2
            );
        });
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ResidenceLevel1BlockEntity(pPos, pState);
    }
}
