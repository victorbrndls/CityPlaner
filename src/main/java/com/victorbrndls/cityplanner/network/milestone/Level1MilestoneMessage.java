package com.victorbrndls.cityplanner.network.milestone;

import com.victorbrndls.cityplanner.city.City;
import com.victorbrndls.cityplanner.city.milestone.Level1Milestone;
import com.victorbrndls.cityplanner.gui.screen.CityFoundationScreen;
import com.victorbrndls.cityplanner.gui.screen.Level1MilestoneRendererImpl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

public record Level1MilestoneMessage(
        int populationRequired,
        int currentPopulation,
        double progress
) {

    public static Level1MilestoneMessage create(City city) {
        return new Level1MilestoneMessage(
                Level1Milestone.POPULATION_REQUIRED,
                city.getResidentCount(),
                city.getCurrentMilestone().progress()
        );
    }

    public static Level1MilestoneMessage decode(FriendlyByteBuf buf) {
        return new Level1MilestoneMessage(
                buf.readInt(),
                buf.readInt(),
                buf.readDouble()
        );
    }

    public void encode(FriendlyByteBuf output) {
        output.writeInt(populationRequired);
        output.writeInt(currentPopulation);
        output.writeDouble(progress);
    }

    public void handle(CustomPayloadEvent.Context context) {
        CityFoundationScreen.renderer = new Level1MilestoneRendererImpl(this);
    }
}