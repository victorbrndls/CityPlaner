package com.victorbrndls.cityplanner.network.milestone;

import com.victorbrndls.cityplanner.city.City;
import com.victorbrndls.cityplanner.city.milestone.Level2Milestone;
import com.victorbrndls.cityplanner.gui.screen.CityFoundationScreen;
import com.victorbrndls.cityplanner.gui.screen.Level2MilestoneRendererImpl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

public record Level2MilestoneMessage(
        int populationRequired,
        int currentPopulation,
        int satisfactionRequired,
        int currentSatisfaction,
        double progress
) {

    public static Level2MilestoneMessage create(City city) {
        return new Level2MilestoneMessage(
                Level2Milestone.POPULATION_REQUIRED,
                city.getResidentCount(),
                Level2Milestone.SATISFACTION_REQUIRED,
                city.getSatisfaction(),
                city.getCurrentMilestone().progress()
        );
    }

    public static Level2MilestoneMessage decode(FriendlyByteBuf buf) {
        return new Level2MilestoneMessage(
                buf.readInt(),
                buf.readInt(),
                buf.readInt(),
                buf.readInt(),
                buf.readDouble()
        );
    }

    public void encode(FriendlyByteBuf output) {
        output.writeInt(populationRequired);
        output.writeInt(currentPopulation);
        output.writeInt(satisfactionRequired);
        output.writeInt(currentSatisfaction);
        output.writeDouble(progress);
    }

    public void handle(CustomPayloadEvent.Context context) {
        CityFoundationScreen.renderer = new Level2MilestoneRendererImpl(this);
    }
}