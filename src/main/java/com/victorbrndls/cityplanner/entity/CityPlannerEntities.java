package com.victorbrndls.cityplanner.entity;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.block.CityFoundationBlockEntity;
import com.victorbrndls.cityplanner.block.CityPlannerBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CityPlannerEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
            ForgeRegistries.BLOCK_ENTITY_TYPES, CityPlannerMod.MODID
    );

    public static final RegistryObject<BlockEntityType<CityFoundationBlockEntity>> CITY_FOUNDATION_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("city_foundation", () ->
                    BlockEntityType.Builder.of(
                            CityFoundationBlockEntity::new, CityPlannerBlocks.CITY_FOUNDATION_BLOCK.get()
                    ).build(null)
            );

}
