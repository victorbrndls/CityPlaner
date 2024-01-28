package com.victorbrndls.cityplanner.block.foundation;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.block.CityPlannerBlockEntities;
import com.victorbrndls.cityplanner.city.City;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CityFoundationBlockEntity extends BlockEntity {

    private City city;
    private CompoundTag persistentData;

    public CityFoundationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(CityPlannerBlockEntities.CITY_FOUNDATION_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        Level level = getLevel();
        if (level == null || level.isClientSide) return;

        city = new City(level, getBlockPos(), this::setChanged);
        CityPlannerMod.citiesController.addCity(city);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();

        Level level = getLevel();
        if (level == null || level.isClientSide) return;

        CityPlannerMod.citiesController.removeCity(city);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        Level level = getLevel();
        if (level == null || level.isClientSide) return;

        city.load(pTag);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        Level level = getLevel();
        if (level == null || level.isClientSide) return;

        city.save(pTag);
    }
}
