package com.victorbrndls.cityplanner.block;

import com.victorbrndls.cityplanner.CityPlannerMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CityPlannerBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
            ForgeRegistries.BLOCKS, CityPlannerMod.MODID
    );

    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () ->
            new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE))
    );


}
