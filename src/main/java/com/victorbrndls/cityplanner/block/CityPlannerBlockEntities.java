package com.victorbrndls.cityplanner.block;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.block.housing.ResidenceLevel1BlockEntity;
import com.victorbrndls.cityplanner.block.industry.LumberMillBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class CityPlannerBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(
            ForgeRegistries.BLOCK_ENTITY_TYPES, CityPlannerMod.MODID
    );

    public static final RegistryObject<BlockEntityType<CityFoundationBlockEntity>> CITY_FOUNDATION_BLOCK_ENTITY =
            register("city_foundation", CityFoundationBlockEntity::new, CityPlannerBlocks.CITY_FOUNDATION_BLOCK);

    // Industry
    public static final RegistryObject<BlockEntityType<LumberMillBlockEntity>> LUMBER_MILL_BLOCK_ENTITY =
            register("lumber_mill", LumberMillBlockEntity::new, CityPlannerBlocks.LUMBER_MILL_BLOCK);

    // Housing
    public static final RegistryObject<BlockEntityType<ResidenceLevel1BlockEntity>> RESIDENCE_LEVEL_1_BLOCK_ENTITY =
            register("residence_level_1", ResidenceLevel1BlockEntity::new, CityPlannerBlocks.RESIDENCE_LEVEL_1_BLOCK);

    public static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(
            String name,
            BlockEntityType.BlockEntitySupplier<T> factory,
            Supplier<Block> block
    ) {
        return BLOCK_ENTITIES.register(name, () -> BlockEntityType.Builder.of(factory, block.get()).build(null));
    }

}
