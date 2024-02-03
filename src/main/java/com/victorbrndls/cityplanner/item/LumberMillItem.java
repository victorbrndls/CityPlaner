package com.victorbrndls.cityplanner.item;

import com.victorbrndls.cityplanner.block.CityPlannerBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class LumberMillItem extends BlockItem {

    public LumberMillItem() {
        super(CityPlannerBlocks.LUMBER_MILL_BLOCK.get(), new Item.Properties());
    }

}
