package com.victorbrndls.cityplanner.network;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.network.milestone.Level1MilestoneMessage;
import com.victorbrndls.cityplanner.network.milestone.Level2MilestoneMessage;
import com.victorbrndls.cityplanner.network.milestone.Level9999MilestoneMessage;
import com.victorbrndls.cityplanner.network.structure.ReceiveCityStructureMessage;
import com.victorbrndls.cityplanner.network.structure.RequestCityStructureMessage;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;

public class CityPlannerNetwork {

    private static final SimpleChannel CHANNEL = CityPlannerMod.CHANNEL;

    public static void register() {
        CHANNEL.messageBuilder(CityStatsMessage.class, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(CityStatsMessage::decode)
                .encoder(CityStatsMessage::encode)
                .consumerMainThread(CityStatsMessage::handle)
                .add();

        CHANNEL.messageBuilder(Level1MilestoneMessage.class, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(Level1MilestoneMessage::decode)
                .encoder(Level1MilestoneMessage::encode)
                .consumerMainThread(Level1MilestoneMessage::handle)
                .add();

        CHANNEL.messageBuilder(Level2MilestoneMessage.class, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(Level2MilestoneMessage::decode)
                .encoder(Level2MilestoneMessage::encode)
                .consumerMainThread(Level2MilestoneMessage::handle)
                .add();

        CHANNEL.messageBuilder(Level9999MilestoneMessage.class, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(Level9999MilestoneMessage::decode)
                .encoder(Level9999MilestoneMessage::encode)
                .consumerMainThread(Level9999MilestoneMessage::handle)
                .add();

        CHANNEL.messageBuilder(RequestCityStructureMessage.class, NetworkDirection.PLAY_TO_SERVER)
                .decoder(RequestCityStructureMessage::new)
                .encoder(RequestCityStructureMessage::encode)
                .consumerMainThread(RequestCityStructureMessage::handle)
                .add();

        CHANNEL.messageBuilder(ReceiveCityStructureMessage.class, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ReceiveCityStructureMessage::new)
                .encoder(ReceiveCityStructureMessage::encode)
                .consumerMainThread(ReceiveCityStructureMessage::handle)
                .add();
    }

}
