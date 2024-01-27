package com.victorbrndls.cityplanner.block.industry;

import com.victorbrndls.cityplanner.CityPlannerMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class LumberMillBlock extends Block implements EntityBlock {

    public LumberMillBlock() {
        super(Blocks.IRON_BLOCK.properties());
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);

        if (pLevel.isClientSide) return;
        if (CityPlannerMod.citiesController.hasCityInRange(pPos)) return;

        pLevel.destroyBlock(pPos, true);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new LumberMillBlockEntity(pPos, pState);
    }
}
