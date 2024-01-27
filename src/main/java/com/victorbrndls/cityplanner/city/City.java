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
        tick1();
        if (currentTick % 5 == 0) tick5();
        if (currentTick % 10 == 0) tick10();
        if (currentTick % 20 == 0) tick20();
        if (currentTick % 40 == 0) tick40();
        if (currentTick % 60 == 0) tick60();

        currentTick++;
        if (currentTick == 61) currentTick = 0;
    }

    private void tick1() {

    }

    private void tick5() {
        industries.forEach(Industry::tick);
    }

    private void tick10() {
        residences.forEach(Residence::tick);
    }

    private void tick20() {
        changed.run(); // TODO: how to improve this?
    }

    private void tick40() {

    }

    private void tick60() {

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

    public void load(CompoundTag pTag) {
        if (pTag.contains(PERSISTENT_DATA_KEY)) this.persistentData = pTag.getCompound(PERSISTENT_DATA_KEY);
    }

    public void save(CompoundTag pTag) {
        if (this.persistentData != null) pTag.put(PERSISTENT_DATA_KEY, this.persistentData.copy());
    }

}
