package com.victorbrndls.cityplanner.city;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class City {

    private static int lastId = 0;

    private final int id;
    private final Level level;
    private final BlockPos position;
    private final Runnable changed;

    private static final String PERSISTENT_DATA_KEY = "CityData";
    private CompoundTag persistentData = new CompoundTag();

    // City related
    private final List<Industry> industries = new ArrayList<>();

    private final List<Residence> residences = new ArrayList<>();

    private final CityResourceManager resourceManager = new CityResourceManager(industries);

    private int currentTick = 0;

    public City(
            Level level,
            BlockPos position,
            Runnable changed
    ) {
        this.id = lastId++;
        this.level = level;
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
        industries.forEach(Industry::tick);
        residences.forEach(Residence::tick);

        if (currentTick == 20) {
            changed.run(); // TODO: how to improve this?
        }

        currentTick++;
        if (currentTick == 61) currentTick = 0;
    }

    // Industry
    public void addIndustry(Industry industry) {
        industries.add(industry);
    }

    public void removeIndustry(Industry industry) {
        industries.remove(industry);
    }

    // Housing
    public void addResidency(Residence residence) {
        residences.add(residence);
    }

    public void removeResidency(Residence residence) {
        residences.remove(residence);
    }

    public boolean tryConsume(Resource resource, int amount) {
        return resourceManager.tryConsume(resource, amount);
    }

    public long getResourceCount(Resource resource) {
        return resourceManager.getResourceCount(resource);
    }

    public int getResidentCount() {
        return residences.stream().mapToInt(Residence::getResidentCount).sum();
    }

    public void load(CompoundTag pTag) {
        if (pTag.contains(PERSISTENT_DATA_KEY)) this.persistentData = pTag.getCompound(PERSISTENT_DATA_KEY);
    }

    public void save(CompoundTag pTag) {
        if (this.persistentData != null) pTag.put(PERSISTENT_DATA_KEY, this.persistentData.copy());
    }

}
