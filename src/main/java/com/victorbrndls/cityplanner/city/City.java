package com.victorbrndls.cityplanner.city;

import com.victorbrndls.cityplanner.city.milestone.Level1Milestone;
import com.victorbrndls.cityplanner.city.milestone.Level2Milestone;
import com.victorbrndls.cityplanner.city.milestone.Milestone;
import com.victorbrndls.cityplanner.city.milestone.MilestoneId;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class City {

    private static int lastId = 0;

    private final int id;
    private final Level level;
    private final BlockPos position;
    private final Runnable changed;

    private static final String PERSISTENT_DATA_KEY = "CityData";
    private static final String MILESTONE_DATA_KEY = "milestone";

    private CompoundTag persistentData = new CompoundTag();

    // City related
    private final List<Industry> industries = new ArrayList<>();

    private final List<Residence> residences = new ArrayList<>();

    private Milestone currentMilestone = new Level1Milestone(this);

    private final ResourceManager resourceManager = new ResourceManager(industries);

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
        } else if (currentTick == 60) {
            checkMilestone();
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

    private MilestoneId getCurrentMilestoneId() {
        var value = persistentData.getString(MILESTONE_DATA_KEY);
        return Arrays.stream(MilestoneId.values())
                .filter(id -> id.id.equals(value))
                .findFirst().orElse(MilestoneId.LEVEL_1);
    }

    private void setCurrentMilestone(MilestoneId id) {
        persistentData.putString(MILESTONE_DATA_KEY, id.id);
    }

    public Milestone getCurrentMilestone() {
        return currentMilestone;
    }

    private void checkMilestone() {
        var isComplete = currentMilestone.isCompleted();
        if (!isComplete) return;

        MilestoneId currentMilestoneId = getCurrentMilestoneId();
        var isLastMilestone = MilestoneId.values()[MilestoneId.values().length - 1] == currentMilestoneId;
        if (isLastMilestone) return;

        var nextMilestoneId = MilestoneId.values()[currentMilestoneId.ordinal() + 1];

        setCurrentMilestone(nextMilestoneId);
        updateMilestoneFromId();
    }

    private void updateMilestoneFromId() {
        var milestoneId = getCurrentMilestoneId();
        if (milestoneId == MilestoneId.LEVEL_1) {
            currentMilestone = new Level1Milestone(this);
        } else if (milestoneId == MilestoneId.LEVEL_2) {
            currentMilestone = new Level2Milestone(this);
        }
    }

    public void load(CompoundTag pTag) {
        if (pTag.contains(PERSISTENT_DATA_KEY)) {
            this.persistentData = pTag.getCompound(PERSISTENT_DATA_KEY);
            updateMilestoneFromId();
        }
    }

    public void save(CompoundTag pTag) {
        if (this.persistentData != null) {
            pTag.put(PERSISTENT_DATA_KEY, this.persistentData.copy());
        }
    }

}
