package com.victorbrndls.cityplanner.item;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.block.CityPlannerBlocks;
import com.victorbrndls.cityplanner.city.structure.CityStructure;
import com.victorbrndls.cityplanner.client.CityPlannerClient;
import com.victorbrndls.cityplanner.client.ClientStructureCache;
import com.victorbrndls.cityplanner.network.structure.RequestCityStructureMessage;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

public class LumberMillItem extends BlockItem {

    public LumberMillItem() {
        super(CityPlannerBlocks.LUMBER_MILL_BLOCK.get(), new Item.Properties());
    }

    // TODO: find better way to request structure
    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (!pLevel.isClientSide) return;

        ClientStructureCache structureCache = CityPlannerClient.STRUCTURE_CACHE;

        if (structureCache.shouldRequest(CityStructure.LUMBER_MILL)) {
            structureCache.requestMade(CityStructure.LUMBER_MILL);

            CityPlannerMod.LOGGER.info("Requesting structure: LUMBER_MILL");
            CityPlannerMod.CHANNEL.send(
                    new RequestCityStructureMessage(CityStructure.LUMBER_MILL),
                    PacketDistributor.SERVER.noArg()
            );
        }
    }
}
