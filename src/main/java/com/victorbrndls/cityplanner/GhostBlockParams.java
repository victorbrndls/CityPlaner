package com.victorbrndls.cityplanner;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class GhostBlockParams {

    private final BlockState state;
    private final BlockPos pos;

    private GhostBlockParams(BlockState state, BlockPos pos) {
        this.state = state;
        this.pos = pos;
    }

    public BlockState getState() {
        return state;
    }

    public BlockPos getPos() {
        return pos;
    }
}
