package com.victorbrndls.cityplanner.client;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public record GhostBlockParams(BlockState state, BlockPos pos) {
}
