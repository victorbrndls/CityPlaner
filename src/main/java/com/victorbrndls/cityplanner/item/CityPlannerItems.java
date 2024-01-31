package com.victorbrndls.cityplanner.item;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.block.CityPlannerBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CityPlannerItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
            ForgeRegistries.ITEMS, CityPlannerMod.MODID
    );

    public static final RegistryObject<Item> CITY_FOUNDATION_ITEM = ITEMS.register("city_foundation", () ->
            new BlockItem(CityPlannerBlocks.CITY_FOUNDATION_BLOCK.get(), new Item.Properties().stacksTo(1))
    );

    // Industry
    public static final RegistryObject<Item> LUMBER_MILL_ITEM = ITEMS.register("lumber_mill", () ->
            new BlockItem(CityPlannerBlocks.LUMBER_MILL_BLOCK.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> VEGETABLE_FARM_ITEM = ITEMS.register("vegetable_farm", () ->
            new BlockItem(CityPlannerBlocks.VEGETABLE_FARM_BLOCK.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> WATER_WELL_ITEM = ITEMS.register("water_well", () ->
            new BlockItem(CityPlannerBlocks.WATER_WELL_BLOCK.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> FURNITURE_FACTORY_ITEM = ITEMS.register("furniture_factory", () ->
            new BlockItem(CityPlannerBlocks.FURNITURE_FACTORY_BLOCK.get(), new Item.Properties())
    );

    // Industry - Energy
    public static final RegistryObject<Item> WIND_TURBINE_ITEM = ITEMS.register("wind_turbine", () ->
            new BlockItem(CityPlannerBlocks.WIND_TURBINE_BLOCK.get(), new Item.Properties())
    );


    // Housing
    public static final RegistryObject<Item> RESIDENCE_LEVEL_1 = ITEMS.register("residence_level_1", () ->
            new BlockItem(CityPlannerBlocks.RESIDENCE_LEVEL_1_BLOCK.get(), new Item.Properties().stacksTo(1))
    );

}
