package com.victorbrndls.cityplanner.network;

import com.victorbrndls.cityplanner.city.City;
import com.victorbrndls.cityplanner.gui.screen.CityFoundationScreen;
import com.victorbrndls.cityplanner.gui.screen.Level9999MilestoneRendererImpl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

public record Level9999MilestoneMessage(
        double progress
) {

    public static Level9999MilestoneMessage create(City city) {
        return new Level9999MilestoneMessage(0.0);
    }

    public static Level9999MilestoneMessage decode(FriendlyByteBuf buf) {
        return new Level9999MilestoneMessage(
                buf.readDouble()
        );
    }

    public void encode(FriendlyByteBuf output) {
        output.writeDouble(progress);
    }

    public void handle(CustomPayloadEvent.Context context) {
        CityFoundationScreen.renderer = new Level9999MilestoneRendererImpl(this);
    }
}