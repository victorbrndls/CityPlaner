package com.victorbrndls.cityplanner.item;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.city.structure.CityStructure;
import com.victorbrndls.cityplanner.client.CityPlannerClient;
import com.victorbrndls.cityplanner.client.ClientStructureCache;
import com.victorbrndls.cityplanner.network.structure.RequestCityStructureMessage;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.network.PacketDistributor;

public class StructureItem extends BlockItem {

    private final CityStructure structure;

    public StructureItem(
            Block block,
            CityStructure structure
    ) {
        super(block, new Item.Properties());
        this.structure = structure;
    }

    // TODO: find better way to request structure
    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (!pLevel.isClientSide) return;

        ClientStructureCache structureCache = CityPlannerClient.STRUCTURE_CACHE;

        if (structureCache.shouldRequest(structure)) {
            structureCache.requestMade(structure);

            CityPlannerMod.LOGGER.info("Requesting structure: " + structure.name());
            CityPlannerMod.CHANNEL.send(
                    new RequestCityStructureMessage(structure),
                    PacketDistributor.SERVER.noArg()
            );
        }
    }

    public CityStructure getStructure() {
        return structure;
    }
}
