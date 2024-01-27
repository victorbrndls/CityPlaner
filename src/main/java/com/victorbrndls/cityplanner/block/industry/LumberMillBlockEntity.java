package com.victorbrndls.cityplanner.block.industry;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.city.City;
import com.victorbrndls.cityplanner.city.CityResource;
import com.victorbrndls.cityplanner.city.Industry;
import com.victorbrndls.cityplanner.entity.CityPlannerEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LumberMillBlockEntity extends BlockEntity implements Industry {

    private City city;
    private Long plankAmount = 0L;

    private int currentTick = 0;

    public LumberMillBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(CityPlannerEntities.LUMBER_MILL_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {
        currentTick++;

        if (currentTick == 8) {
            currentTick = 0;
            plankAmount++;
        }
    }

    @Override
    public Long getResourceCount(CityResource resource) {
        if (resource == CityResource.PLANK) return plankAmount;
        return 0L;
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

        // remove from city
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        Level level = getLevel();
        if (level == null || level.isClientSide) return;

        plankAmount = pTag.getLong("plankAmount");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        Level level = getLevel();
        if (level == null || level.isClientSide) return;

        pTag.putLong("plankAmount", plankAmount);
    }
}
