package com.victorbrndls.cityplanner.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public class CityHelper {

    public static void displayMessageToNearbyPlayers(Level level, BlockPos pos, String message) {
        level.players().stream()
                .filter(player -> BlockPosHelper.isInHorizontalDistance(pos, player.blockPosition(), 10))
                .forEach(player -> player.displayClientMessage(Component.literal(message), true));
    }

}
