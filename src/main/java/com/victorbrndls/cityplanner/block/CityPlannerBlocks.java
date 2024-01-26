package com.victorbrndls.cityplanner.block;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.block.industry.LumberMillBlock;
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

    public static final RegistryObject<Block> LUMBER_MILL_BLOCK = BLOCKS.register("lumber_mill",
            LumberMillBlock::new
    );

}
