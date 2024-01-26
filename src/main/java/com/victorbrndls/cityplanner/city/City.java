package com.victorbrndls.cityplanner.city;

import net.minecraft.core.BlockPos;

public class City {

    private static int lastId = 0;

    private final int id;
    private final BlockPos position;

    public City(BlockPos position) {
        this.id = lastId++;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public BlockPos getPosition() {
        return position;
    }

    public void tick() {

    }
}
