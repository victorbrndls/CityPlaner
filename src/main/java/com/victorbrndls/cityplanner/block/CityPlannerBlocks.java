package com.victorbrndls.cityplanner.block;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.block.foundation.CityFoundationBlock;
import com.victorbrndls.cityplanner.block.housing.ResidenceLevel1Block;
import com.victorbrndls.cityplanner.block.industry.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CityPlannerBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
            ForgeRegistries.BLOCKS, CityPlannerMod.MODID
    );

    public static final RegistryObject<Block> CITY_FOUNDATION_BLOCK = BLOCKS.register("city_foundation",
            CityFoundationBlock::new
    );

    // Industry
    public static final RegistryObject<Block> LUMBER_MILL_BLOCK = BLOCKS.register("lumber_mill",
            LumberMillBlock::new
    );
    public static final RegistryObject<Block> VEGETABLE_FARM_BLOCK = BLOCKS.register("vegetable_farm",
            VegetableFarmBlock::new
    );
    public static final RegistryObject<Block> WATER_WELL_BLOCK = BLOCKS.register("water_well",
            WaterWellBlock::new
    );
    public static final RegistryObject<Block> FURNITURE_FACTORY_BLOCK = BLOCKS.register("furniture_factory",
            FurnitureFactoryBlock::new
    );

    // Industry - Energy
    public static final RegistryObject<Block> WIND_TURBINE_BLOCK = BLOCKS.register("wind_turbine",
            WindTurbineBlock::new
    );

    // Housing
    public static final RegistryObject<Block> RESIDENCE_LEVEL_1_BLOCK = BLOCKS.register("residence_level_1",
            ResidenceLevel1Block::new
    );

}
