package com.victorbrndls.cityplanner.helper;

import net.minecraft.core.BlockPos;

public class BlockPosHelper {

    public static boolean isInHorizontalDistance(BlockPos pos1, BlockPos pos2, int distance) {
        int diffX = Math.abs(pos1.getX() - pos2.getX());
        int diffZ = Math.abs(pos1.getZ() - pos2.getZ());
        return diffX < distance && diffZ < distance;
    }

}
