package com.victorbrndls.cityplanner.block.industry;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.block.CityPlannerBlockEntities;
import com.victorbrndls.cityplanner.city.City;
import com.victorbrndls.cityplanner.city.Industry;
import com.victorbrndls.cityplanner.city.Resource;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WaterWellBlockEntity extends BlockEntity implements Industry {

    private City city;
    private Long waterAmount = 0L;

    private Long maxStorage = 40L;

    private int currentTick = 0;

    public WaterWellBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(CityPlannerBlockEntities.WATER_WELL_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {
        currentTick++;

        if (currentTick == 20) {
            currentTick = 0;

            if (waterAmount <= maxStorage - 8) {
                waterAmount += 8;
                setChanged();
            }
        }
    }

    @Override
    public Long getResourceCount(Resource resource) {
        if (resource == Resource.WATER) return waterAmount;
        return 0L;
    }

    @Override
    public void consumeResource(Resource resource, int amount) {
        if (resource == Resource.WATER) waterAmount -= amount;
    }

    @Override
    public void onLoad() {
        super.onLoad();

        Level level = getLevel();
        if (level == null || level.isClientSide) return;

        CityPlannerMod.citiesController.getCityInRange(getBlockPos(), (city) -> {
            city.addIndustry(this);
            this.city = city;
        });
    }

    @Override
    public void setRemoved() {
        super.setRemoved();

        Level level = getLevel();
        if (level == null || level.isClientSide) return;

        if (city == null) return;
        city.removeIndustry(this);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        currentTick = pTag.getInt("currentTick");
        waterAmount = pTag.getLong("waterAmount");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("currentTick", currentTick);
        pTag.putLong("waterAmount", waterAmount);
    }
}
