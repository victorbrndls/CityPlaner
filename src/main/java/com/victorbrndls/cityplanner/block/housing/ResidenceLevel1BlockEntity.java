package com.victorbrndls.cityplanner.block.housing;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.block.CityPlannerBlockEntities;
import com.victorbrndls.cityplanner.city.City;
import com.victorbrndls.cityplanner.city.Resource;
import com.victorbrndls.cityplanner.city.housing.Residence;
import com.victorbrndls.cityplanner.city.housing.Resident;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ResidenceLevel1BlockEntity extends BlockEntity implements Residence {

    Random random = new Random();

    private City city;

    private List<Resident> residents = new ArrayList<>();
    private static final int maxResidents = 4;

    private int currentTick = 0;

    public ResidenceLevel1BlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(CityPlannerBlockEntities.RESIDENCE_LEVEL_1_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {
        currentTick++;

        if (currentTick == 40) {
            currentTick = 0;

            tryEating();

            removeDeadResidents();
            spawnNewResident();
        }
    }

    @Override
    public int getResidentCount() {
        return residents.size();
    }

    @Override
    public int satisfaction() {
        if (residents.isEmpty()) return 0;

        var totalSatisfaction = 0;

        for (var resident : residents) {
            totalSatisfaction += resident.satisfaction();
        }

        return totalSatisfaction / residents.size();
    }

    private void tryEating() {
        for (var resident : residents) {
            var hasFood = city.tryConsume(Resource.VEGETABLE, 1);

            if (hasFood) {
                resident.onResourceAvailable(Resource.VEGETABLE);
            } else {
                resident.onResourceMissing(Resource.VEGETABLE);
            }
        }

        setChanged();
    }

    private void removeDeadResidents() {
        var dead = residents.stream().filter(resident -> !resident.isAlive()).toList();
        dead.forEach(this::onResidentDied);
    }

    private void spawnNewResident() {
        if (residents.size() < maxResidents) {
            if (random.nextInt(0, 15) == 0) {
                // At least 10 vegetables required
                if (city.tryConsume(Resource.VEGETABLE, 10)) {
                    residents.add(new Resident());
                    setChanged();
                }
            }
        }
    }

    private void onResidentDied(Resident resident) {
        residents.remove(resident);
        setChanged();
    }

    @Override
    public void onLoad() {
        super.onLoad();

        Level level = getLevel();
        if (level == null || level.isClientSide) return;

        CityPlannerMod.citiesController.getCityInRange(getBlockPos(), (city) -> {
            city.addResidency(this);
            this.city = city;
        });
    }

    @Override
    public void setRemoved() {
        super.setRemoved();

        Level level = getLevel();
        if (level == null || level.isClientSide) return;

        if (city == null) return;
        city.removeResidency(this);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        currentTick = pTag.getInt("currentTick");

        ListTag residentsTag = (ListTag) pTag.get("residents");
        residentsTag.forEach(residentTag -> {
            Resident resident = new Resident();
            resident.load((CompoundTag) residentTag);
            residents.add(resident);
        });
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("currentTick", currentTick);

        ListTag residentsTag = new ListTag();
        residents.forEach(resident -> {
            CompoundTag residentTag = new CompoundTag();
            resident.save(residentTag);
            residentsTag.add(residentTag);
        });

        pTag.put("residents", residentsTag);
    }
}
