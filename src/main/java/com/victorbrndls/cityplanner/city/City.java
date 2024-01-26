package com.victorbrndls.cityplanner.city;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class City {

    private static int lastId = 0;

    private final int id;
    private final BlockPos position;
    private final Runnable changed;

    private static final String PERSISTENT_DATA_KEY = "CityData";
    private CompoundTag persistentData = new CompoundTag();

    public City(
            BlockPos position,
            Runnable changed
    ) {
        this.id = lastId++;
        this.position = position;
        this.changed = changed;
    }

    public int getId() {
        return id;
    }

    public BlockPos getPosition() {
        return position;
    }

    public void tick() {


        changed.run();
    }

    public void load(CompoundTag pTag) {
        if (pTag.contains(PERSISTENT_DATA_KEY)) this.persistentData = pTag.getCompound(PERSISTENT_DATA_KEY);
    }

    public void save(CompoundTag pTag) {
        if (this.persistentData != null) pTag.put(PERSISTENT_DATA_KEY, this.persistentData.copy());
    }
}
