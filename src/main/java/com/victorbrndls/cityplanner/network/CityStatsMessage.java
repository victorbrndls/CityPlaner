package com.victorbrndls.cityplanner.network;

import com.victorbrndls.cityplanner.city.City;
import com.victorbrndls.cityplanner.city.Resource;
import com.victorbrndls.cityplanner.gui.CityStatsRenderer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

public record CityStatsMessage(
        long waterAmount,
        long energyAmount,
        long plankAmount,
        long vegetableAmount,
        long furnitureAmount,
        long population,
        int satisfaction
) {

    public static CityStatsMessage create(City city) {
        return new CityStatsMessage(
                city.getResourceCount(Resource.WATER),
                city.getResourceCount(Resource.ENERGY),
                city.getResourceCount(Resource.PLANK),
                city.getResourceCount(Resource.VEGETABLE),
                city.getResourceCount(Resource.FURNITURE),
                city.getResidentCount(),
                city.getSatisfaction()
        );
    }

    public static CityStatsMessage decode(FriendlyByteBuf buf) {
        return new CityStatsMessage(
                buf.readLong(),
                buf.readLong(),
                buf.readLong(),
                buf.readLong(),
                buf.readLong(),
                buf.readLong(),
                buf.readInt()
        );
    }

    public void encode(FriendlyByteBuf output) {
        output.writeLong(waterAmount);
        output.writeLong(energyAmount);
        output.writeLong(plankAmount);
        output.writeLong(vegetableAmount);
        output.writeLong(furnitureAmount);
        output.writeLong(population);
        output.writeInt(satisfaction);
    }

    public void handle(CustomPayloadEvent.Context context) {
        CityStatsRenderer.message = this;
    }
}