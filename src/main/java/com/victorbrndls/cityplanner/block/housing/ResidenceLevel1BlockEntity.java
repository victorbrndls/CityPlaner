package com.victorbrndls.cityplanner.block.housing;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.block.CityPlannerBlockEntities;
import com.victorbrndls.cityplanner.city.City;
import com.victorbrndls.cityplanner.city.Residence;
import com.victorbrndls.cityplanner.city.Resource;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Date;
import java.util.Random;

public class ResidenceLevel1BlockEntity extends BlockEntity implements Residence {

    private static final int FIVE_SECONDS_MS = 5 * 1000;
    private static final int TEN_SECONDS_MS = FIVE_SECONDS_MS * 2;

    Random random = new Random();

    private City city;

    private int residents = 0;
    private static final int maxResidents = 4;

    private int satisfaction = 0;

    private Date lastConsumedFood = new Date(0);
    private Date lastConsumedEnergy = new Date(0);
    private Date lastConsumedFurniture = new Date(0);
    private Date lastDied = new Date(0);

    private int currentTick = 0;
    private Date now = new Date();

    public ResidenceLevel1BlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(CityPlannerBlockEntities.RESIDENCE_LEVEL_1_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {
        currentTick++;

        if (currentTick == 40) {
            currentTick = 0;
            now = new Date();

            if (residents > 0) {
                tryConsumingFood();
                tryConsumingEnergy();
                tryConsumingFurniture();
            }

            updateSatisfaction();

            spawnNewResident();
        }
    }

    @Override
    public int getResidentCount() {
        return residents;
    }

    @Override
    public int satisfaction() {
        return satisfaction;
    }

    private void tryConsumingFood() {
        var hasFood = city.tryConsume(Resource.VEGETABLE, residents);

        if (hasFood) {
            onResourceAvailable(Resource.VEGETABLE);
        } else {
            onResourceMissing(Resource.VEGETABLE);
        }

        setChanged();
    }

    private void tryConsumingEnergy() {
        if (lastConsumedEnergy.getTime() + FIVE_SECONDS_MS < now.getTime()) {
            int energyToConsume = residents / 2;
            if (energyToConsume == 0) energyToConsume = 1;

            if (city.tryConsume(Resource.ENERGY, energyToConsume)) {
                onResourceAvailable(Resource.ENERGY);
            } else {
                onResourceMissing(Resource.ENERGY);
            }
        }
    }

    private void tryConsumingFurniture() {
        if (lastConsumedFurniture.getTime() + TEN_SECONDS_MS < now.getTime()) {
            int furnitureToConsume = residents / 2;
            if (furnitureToConsume == 0) furnitureToConsume = 1;

            if (city.tryConsume(Resource.FURNITURE, furnitureToConsume)) {
                onResourceAvailable(Resource.FURNITURE);
            } else {
                onResourceMissing(Resource.FURNITURE);
            }
        }
    }

    private void onResourceAvailable(Resource resource) {
        switch (resource) {
            case VEGETABLE -> lastConsumedFood = now;
            case ENERGY -> lastConsumedEnergy = now;
            case FURNITURE -> lastConsumedFurniture = now;
        }
    }

    private void onResourceMissing(Resource resource) {
        if (resource == Resource.VEGETABLE) {
            onNoFood();
        }
    }

    private void onNoFood() {
        if (random.nextInt(0, 4) == 0) { // 25% chance of dying
            residents--;
            lastDied = now;
            setChanged();
        }
    }

    private void spawnNewResident() {
        if (residents < maxResidents) {
            if (random.nextInt(0, 15) == 0) {
                // At least 10 vegetables required
                if (city.tryConsume(Resource.VEGETABLE, 10)) {
                    residents++;
                    setChanged();
                }
            }
        }
    }

    private void updateSatisfaction() {
        if (residents == 0) {
            this.satisfaction = 0;
            return;
        }

        var satisfaction = 0;

        if (lastConsumedFood.getTime() + TEN_SECONDS_MS > now.getTime()) {
            satisfaction = 60;
        } else {
            satisfaction = 5;
        }

        if (lastConsumedEnergy.getTime() + FIVE_SECONDS_MS > now.getTime()) {
            satisfaction += 7;
        }

        if (lastConsumedFurniture.getTime() + TEN_SECONDS_MS > now.getTime()) {
            satisfaction += 5;
        }

        if (lastDied.getTime() + TEN_SECONDS_MS > now.getTime()) {
            satisfaction -= 15;
        }

        this.satisfaction = satisfaction;
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
        residents = pTag.getInt("residents2");
        satisfaction = pTag.getInt("satisfaction");
        lastConsumedFood = new Date(pTag.getLong("lastConsumedFood"));
        lastConsumedEnergy = new Date(pTag.getLong("lastConsumedEnergy"));
        lastConsumedFurniture = new Date(pTag.getLong("lastConsumedFurniture"));
        lastDied = new Date(pTag.getLong("lastDied"));
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("currentTick", currentTick);
        pTag.putInt("residents2", residents);
        pTag.putInt("satisfaction", satisfaction);
        pTag.putLong("lastConsumedFood", lastConsumedFood.getTime());
        pTag.putLong("lastConsumedEnergy", lastConsumedEnergy.getTime());
        pTag.putLong("lastConsumedFurniture", lastConsumedFurniture.getTime());
        pTag.putLong("lastDied", lastDied.getTime());
    }
}
