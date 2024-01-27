package com.victorbrndls.cityplanner.block.housing;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.block.CityPlannerBlockEntities;
import com.victorbrndls.cityplanner.city.City;
import com.victorbrndls.cityplanner.city.Residence;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ResidenceLevel1BlockEntity extends BlockEntity implements Residence {

    private City city;

    private int currentTick = 0;

    public ResidenceLevel1BlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(CityPlannerBlockEntities.RESIDENCE_LEVEL_1_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {
        currentTick++;

        if (currentTick == 8) {
            currentTick = 0;

        }
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

        Level level = getLevel();
        if (level == null || level.isClientSide) return;

//        plankAmount = pTag.getLong("plankAmount");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        Level level = getLevel();
        if (level == null || level.isClientSide) return;

//        pTag.putLong("plankAmount", plankAmount);
    }
}
