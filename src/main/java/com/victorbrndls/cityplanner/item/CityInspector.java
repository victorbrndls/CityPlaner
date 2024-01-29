package com.victorbrndls.cityplanner.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CityInspector extends Item {

    private int currentTick = 0;

    public CityInspector() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);

        if (!pLevel.isClientSide) return;

        currentTick++;

        if (currentTick == 20) {
            currentTick = 0;


        }
    }
}
