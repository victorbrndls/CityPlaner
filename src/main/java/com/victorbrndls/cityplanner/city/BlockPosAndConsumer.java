package com.victorbrndls.cityplanner.city;

import net.minecraft.core.BlockPos;

import java.util.function.Consumer;

public record BlockPosAndConsumer(BlockPos pos, Consumer<City> onCityFound) {

}
