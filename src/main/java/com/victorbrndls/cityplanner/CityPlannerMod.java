package com.victorbrndls.cityplanner;

import com.mojang.logging.LogUtils;
import com.victorbrndls.cityplanner.block.CityPlannerBlockEntities;
import com.victorbrndls.cityplanner.block.CityPlannerBlocks;
import com.victorbrndls.cityplanner.city.CitiesController;
import com.victorbrndls.cityplanner.creative_tab.CityPlannerCreativeTabs;
import com.victorbrndls.cityplanner.gui.CityStatsRenderer;
import com.victorbrndls.cityplanner.item.CityPlannerItems;
import com.victorbrndls.cityplanner.network.CityStatsMessage;
import com.victorbrndls.cityplanner.network.Level1MilestoneMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;
import org.slf4j.Logger;

@Mod(CityPlannerMod.MODID)
public class CityPlannerMod {

    public static final String MODID = "cityplanner";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static CitiesController citiesController;

    public static final SimpleChannel CHANNEL = ChannelBuilder.named(new ResourceLocation(MODID, "main")).simpleChannel();

    public CityPlannerMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        CityPlannerBlocks.BLOCKS.register(modEventBus);
        CityPlannerItems.ITEMS.register(modEventBus);
        CityPlannerBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        CityPlannerCreativeTabs.TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        CHANNEL.messageBuilder(
                        CityStatsMessage.class,
                        NetworkDirection.PLAY_TO_CLIENT
                )
                .decoder(CityStatsMessage::decode)
                .encoder(CityStatsMessage::encode)
                .consumerMainThread(CityStatsMessage::handle)
                .add();

        CHANNEL.messageBuilder(
                        Level1MilestoneMessage.class,
                        NetworkDirection.PLAY_TO_CLIENT
                )
                .decoder(Level1MilestoneMessage::decode)
                .encoder(Level1MilestoneMessage::encode)
                .consumerMainThread(Level1MilestoneMessage::handle)
                .add();
    }

    @SubscribeEvent
    public void onLevelTick(TickEvent.LevelTickEvent e) {
        if (!e.level.isClientSide() && e.phase == TickEvent.Phase.END) {
            CitiesController controller = CityPlannerMod.citiesController;
            if (controller == null) return;

            controller.tick(e.level);
        }
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

    @SubscribeEvent
    public void guiRendered(final RenderGuiEvent.Post event) {
        CityStatsRenderer.render(event.getGuiGraphics(), event.getPartialTick());
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
