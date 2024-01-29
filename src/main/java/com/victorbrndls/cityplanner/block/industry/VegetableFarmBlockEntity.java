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

public class VegetableFarmBlockEntity extends BlockEntity implements Industry {

    private City city;
    private Long vegetableAmount = 0L;

    private Long maxStorage = 20L;

    private int currentTick = 0;

    public VegetableFarmBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(CityPlannerBlockEntities.VEGETABLE_FARM_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {
        currentTick++;

        if (currentTick == 20) {
            currentTick = 0;

            if (vegetableAmount < maxStorage) {
                vegetableAmount += 2;
                setChanged();
            }
        }
    }

    @Override
    public Long getResourceCount(Resource resource) {
        if (resource == Resource.VEGETABLE) return vegetableAmount;
        return 0L;
    }

    @Override
    public void consumeResource(Resource resource, int amount) {
        if (resource == Resource.VEGETABLE) vegetableAmount -= amount;
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
        vegetableAmount = pTag.getLong("vegetableAmount");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("currentTick", currentTick);
        pTag.putLong("vegetableAmount", vegetableAmount);
    }
}
