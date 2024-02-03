package com.victorbrndls.cityplanner.network.structure;

import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.city.structure.CityStructure;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.Optional;

public class RequestCityStructureMessage {
    private final CityStructure structure;

    public RequestCityStructureMessage(CityStructure structure) {
        this.structure = structure;
    }

    public RequestCityStructureMessage(FriendlyByteBuf pBuffer) {
        this.structure = CityStructure.values()[pBuffer.readInt()];
    }

    public void encode(FriendlyByteBuf pBuffer) {
        pBuffer.writeInt(structure.ordinal());
    }

    public void handle(CustomPayloadEvent.Context context) {
        var sender = context.getSender();
        var level = context.getSender().serverLevel();

        var structureManager = level.getStructureManager();

        var resourcePath = switch (structure) {
            case LUMBER_MILL -> "lumber_mill";
        };

        // TODO: cache on server side too
        ResourceLocation resourceLocation = new ResourceLocation(CityPlannerMod.MODID, resourcePath);
        Optional<StructureTemplate> structureTemplate = structureManager.get(resourceLocation);

        structureTemplate.ifPresent((struct) -> {
            StructureTemplate.Palette palette = struct.palettes.get(0);
            palette.blocks().removeIf(blockInfo -> blockInfo.state().isAir());

            var pos = palette.blocks.stream().map(StructureTemplate.StructureBlockInfo::pos).toList();
            var state = palette.blocks.stream().map(StructureTemplate.StructureBlockInfo::state).toList();

            CityPlannerMod.CHANNEL.send(
                    new ReceiveCityStructureMessage(structure, pos, state),
                    PacketDistributor.PLAYER.with(sender)
            );
        });
    }

}