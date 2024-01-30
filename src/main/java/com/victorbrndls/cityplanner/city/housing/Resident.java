package com.victorbrndls.cityplanner.city.housing;

import com.victorbrndls.cityplanner.city.Resource;
import net.minecraft.nbt.CompoundTag;

import java.util.Date;
import java.util.Random;

public class Resident {

    private static final int TEN_SECONDS_MS = 10 * 1000;

    private Random random = new Random();

    private Date lastEatenAt = new Date(0);
    private int satisfaction = 0;

    private boolean isAlive = true;

    public void onResourceAvailable(Resource resource) {
        if (resource == Resource.VEGETABLE) {
            lastEatenAt = new Date();
        }

        updateSatisfaction();
    }

    public void onResourceMissing(Resource resource) {
        if (resource == Resource.VEGETABLE) {
            onNoFood();
        }

        updateSatisfaction();
    }

    private void onNoFood() {
        if (random.nextInt(0, 5) == 0) { // 20% chance of dying
            isAlive = false;
        }
    }

    private void updateSatisfaction() {
        if (lastEatenAt.getTime() + TEN_SECONDS_MS > new Date().getTime()) {
            satisfaction = 65;
        } else {
            satisfaction = 5;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public int satisfaction() {
        return satisfaction;
    }

    public void load(CompoundTag tag) {
        tag.putLong("lastEatenAt", lastEatenAt.getTime());
        tag.putInt("satisfaction", satisfaction);
    }

    public void save(CompoundTag tag) {
        lastEatenAt = new Date(tag.getLong("lastEatenAt"));
        satisfaction = tag.getInt("satisfaction");
    }
}
