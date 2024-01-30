package com.victorbrndls.cityplanner.block.housing;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.city.City;
import com.victorbrndls.cityplanner.city.Resource;
import com.victorbrndls.cityplanner.helper.CityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.Nullable;

public class ResidenceLevel1Block extends Block implements EntityBlock {

    public ResidenceLevel1Block() {
        super(Blocks.SPRUCE_WOOD.properties()
                .pushReaction(PushReaction.BLOCK)
        );
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);

        if (pLevel.isClientSide) return;

        City city = CityPlannerMod.citiesController.getCityInRange(pPos);
        if (city != null) {
            var hasResources = city.tryConsume(Resource.PLANK, 10);
            if (hasResources) {
                return;
            } else {
                CityHelper.displayMessageToNearbyPlayers(pLevel, pPos, "You need 10 planks to build a residence");
            }
        } else {
            CityHelper.displayMessageToNearbyPlayers(pLevel, pPos, "No city in range");
        }

        pLevel.destroyBlock(pPos, true);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ResidenceLevel1BlockEntity(pPos, pState);
    }
}
