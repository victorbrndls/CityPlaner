package com.victorbrndls.cityplanner.city.housing;

import net.minecraft.nbt.CompoundTag;

import java.util.Date;
import java.util.Random;

public class Resident {

    private Random random = new Random();

    private Date lastEatenAt = new Date(0);

    private boolean isAlive = true;

    public void onFoodAvailable() {
        lastEatenAt = new Date();
    }

    public void onNoFood() {
        if (random.nextInt(0, 5) == 0) { // 20% chance of dying
            isAlive = false;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int satisfaction() {
        return 0;
    }

    public void load(CompoundTag tag) {
        tag.putLong("lastEatenAt", lastEatenAt.getTime());
    }

    public void save(CompoundTag tag) {
        lastEatenAt = new Date(tag.getLong("lastEatenAt"));
    }
}
