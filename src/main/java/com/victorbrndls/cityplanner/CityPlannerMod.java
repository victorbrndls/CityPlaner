package com.victorbrndls.cityplanner;

import com.mojang.logging.LogUtils;
import com.victorbrndls.cityplanner.block.CityPlannerBlocks;
import com.victorbrndls.cityplanner.city.CitiesController;
import com.victorbrndls.cityplanner.creative_tab.CityPlannerCreativeTabs;
import com.victorbrndls.cityplanner.entity.CityPlannerEntities;
import com.victorbrndls.cityplanner.item.CityPlannerItems;
import com.victorbrndls.cityplanner.network.NetworkListener;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(CityPlannerMod.MODID)
public class CityPlannerMod {

    public static final String MODID = "cityplanner";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static CitiesController citiesController;

    public CityPlannerMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CityPlannerBlocks.BLOCKS.register(modEventBus);
        CityPlannerItems.ITEMS.register(modEventBus);
        CityPlannerEntities.BLOCK_ENTITIES.register(modEventBus);
        CityPlannerCreativeTabs.TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new NetworkListener());

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public void levelLoaded(final LevelEvent.Load event) {
        synchronized (this) {
            if (citiesController != null) return;

            if (!event.getLevel().isClientSide()) {
                citiesController = new CitiesController();
                LOGGER.info("Created controller");
            }
        }
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
