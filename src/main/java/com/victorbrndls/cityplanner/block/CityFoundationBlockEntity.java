package com.victorbrndls.cityplanner.block;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.city.City;
import com.victorbrndls.cityplanner.entity.CityPlannerEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CityFoundationBlockEntity extends BlockEntity {

    public CityFoundationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(CityPlannerEntities.CITY_FOUNDATION_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        Level level = getLevel();
        if (level == null || level.isClientSide) return;

        CityPlannerMod.citiesController.addCity(new City(getBlockPos()));
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        CityPlannerMod.citiesController.removeCity(getBlockPos());
    }
}
