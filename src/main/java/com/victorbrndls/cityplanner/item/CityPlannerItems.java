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

    public static final RegistryObject<Item> LUMBER_MILL_ITEM = ITEMS.register("lumber_mill", () ->
            new BlockItem(CityPlannerBlocks.LUMBER_MILL_BLOCK.get(), new Item.Properties())
    );

}
