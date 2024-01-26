package com.victorbrndls.cityplanner.block.industry;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.city.City;
import com.victorbrndls.cityplanner.city.Industry;
import com.victorbrndls.cityplanner.entity.CityPlannerEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LumberMillBlockEntity extends BlockEntity implements Industry {

    private City city;

    public LumberMillBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(CityPlannerEntities.LUMBER_MILL_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {

    }

    @Override
    public void onLoad() {
        super.onLoad();

        Level level = getLevel();
        if (level == null || level.isClientSide) return;

        city = CityPlannerMod.citiesController.getCityInRange(getBlockPos());
        city.addIndustry(this);
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


    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        Level level = getLevel();
        if (level == null || level.isClientSide) return;


    }
}
