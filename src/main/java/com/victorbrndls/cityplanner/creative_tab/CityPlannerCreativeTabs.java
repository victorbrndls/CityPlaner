package com.victorbrndls.cityplanner.creative_tab;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.item.CityPlannerItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CityPlannerCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(
            Registries.CREATIVE_MODE_TAB, CityPlannerMod.MODID
    );

    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = TABS.register("example_tab", () ->
            CreativeModeTab.builder()
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> CityPlannerItems.CITY_FOUNDATION_ITEM.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(CityPlannerItems.CITY_FOUNDATION_ITEM.get());
                    }).build());

}
