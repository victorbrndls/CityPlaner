package com.victorbrndls.cityplanner.city.structure;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public record CityStructureInfo(CityStructure structure, List<BlockPos> pos, List<BlockState> blockState) {

}
