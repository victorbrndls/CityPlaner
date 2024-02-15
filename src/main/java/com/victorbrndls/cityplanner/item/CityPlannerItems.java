package com.victorbrndls.cityplanner.item;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.block.CityPlannerBlocks;
import com.victorbrndls.cityplanner.city.structure.CityStructure;
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
            new StructureItem(CityPlannerBlocks.LUMBER_MILL_BLOCK.get(), CityStructure.LUMBER_MILL)
    );
    public static final RegistryObject<Item> VEGETABLE_FARM_ITEM = ITEMS.register("vegetable_farm", () ->
            new StructureItem(CityPlannerBlocks.VEGETABLE_FARM_BLOCK.get(), CityStructure.VEGETABLE_FARM)
    );
    public static final RegistryObject<Item> WATER_WELL_ITEM = ITEMS.register("water_well", () ->
            new StructureItem(CityPlannerBlocks.WATER_WELL_BLOCK.get(), CityStructure.WATER_WELL)
    );
    public static final RegistryObject<Item> FURNITURE_FACTORY_ITEM = ITEMS.register("furniture_factory", () ->
            new StructureItem(CityPlannerBlocks.FURNITURE_FACTORY_BLOCK.get(), CityStructure.FURNITURE_FACTORY)
    );

    // Industry - Energy
    public static final RegistryObject<Item> WIND_TURBINE_ITEM = ITEMS.register("wind_turbine", () ->
            new StructureItem(CityPlannerBlocks.WIND_TURBINE_BLOCK.get(), CityStructure.WIND_TURBINE)
    );

    // Housing
    public static final RegistryObject<Item> RESIDENCE_LEVEL_1 = ITEMS.register("residence_level_1", () ->
            new StructureItem(CityPlannerBlocks.RESIDENCE_LEVEL_1_BLOCK.get(), CityStructure.RESIDENCE_1)
    );

}
