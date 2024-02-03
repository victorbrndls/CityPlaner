package com.victorbrndls.cityplanner.network.structure;

import com.google.common.collect.Lists;
import com.victorbrndls.cityplanner.CityPlannerMod;
import com.victorbrndls.cityplanner.city.structure.CityStructure;
import com.victorbrndls.cityplanner.city.structure.CityStructureInfo;
import com.victorbrndls.cityplanner.client.CityPlannerClient;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.network.CustomPayloadEvent;

import java.util.ArrayList;
import java.util.List;

public class ReceiveCityStructureMessage {
    private final CityStructure structure;
    private final List<BlockPos> pos;
    private final List<BlockState> blockState;

    public ReceiveCityStructureMessage(CityStructure structure, List<BlockPos> pPos, List<BlockState> pBlockState) {
        this.structure = structure;
        this.pos = pPos;
        this.blockState = pBlockState;
    }

    public ReceiveCityStructureMessage(FriendlyByteBuf pBuffer) {
        this.structure = CityStructure.values()[pBuffer.readInt()];

        var pairs = pBuffer.readCollection(Lists::newArrayListWithCapacity, Pair::readFromBuffer);
        this.pos = pairs.stream().map(pair -> pair.pos).toList();
        this.blockState = pairs.stream().map(pair -> pair.blockState).toList();
    }

    public void encode(FriendlyByteBuf pBuffer) {
        var collection = new ArrayList<Pair>();

        for (int i = 0; i < pos.size(); i++) {
            collection.add(new Pair(pos.get(i), blockState.get(i)));
        }

        pBuffer.writeInt(structure.ordinal());
        pBuffer.writeCollection(collection, Pair::writeToBuffer);
    }

    public void handle(CustomPayloadEvent.Context context) {
        CityPlannerMod.LOGGER.info("Received structure: " + structure.name());
        var info = new CityStructureInfo(structure, pos, blockState);
        CityPlannerClient.STRUCTURE_CACHE.add(info);
    }

    private static class Pair {
        final BlockPos pos;
        final BlockState blockState;

        public Pair(BlockPos pPos, BlockState pBlockState) {
            this.pos = pPos;
            this.blockState = pBlockState;
        }

        public static void writeToBuffer(FriendlyByteBuf buffer, Pair pair) {
            buffer.writeBlockPos(pair.pos);
            buffer.writeId(Block.BLOCK_STATE_REGISTRY, pair.blockState);
        }

        public static Pair readFromBuffer(FriendlyByteBuf buffer) {
            BlockPos pos = buffer.readBlockPos();
            BlockState blockState = buffer.readById(Block.BLOCK_STATE_REGISTRY);
            return new Pair(pos, blockState);
        }
    }
}