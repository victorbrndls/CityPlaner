package com.victorbrndls.cityplanner.block.industry;

import net.minecraft.core.BlockPos;
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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new LumberMillBlockEntity(pPos, pState);
    }
}
