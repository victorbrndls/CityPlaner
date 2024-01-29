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

import java.util.Random;

public class ResidenceLevel1BlockEntity extends BlockEntity implements Residence {

    Random random = new Random();

    private City city;

    private int residents = 0;
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

            eatOrDie();
            spawnNewResident();
        }
    }

    @Override
    public int getResidentCount() {
        return residents;
    }

    private void eatOrDie() {
        for (int i = 0; i < residents; i++) {
            var ate = city.tryConsume(Resource.VEGETABLE, 1);
            if (!ate) killResident();
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

    private void killResident() {
        residents--;
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
        residents = pTag.getInt("residents");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("currentTick", currentTick);
        pTag.putInt("residents", residents);
    }
}
