package com.victorbrndls.cityplanner.network;

import com.google.common.collect.Lists;
import com.victorbrndls.cityplanner.client.CityPlannerClient;
import com.victorbrndls.cityplanner.client.GhostBlockParams;
import com.victorbrndls.cityplanner.client.GhostBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.network.CustomPayloadEvent;

import java.util.ArrayList;
import java.util.List;

public class StructureMessage {
    private final List<BlockPos> pos;
    private final List<BlockState> blockState;

    public StructureMessage(List<BlockPos> pPos, List<BlockState> pBlockState) {
        this.pos = pPos;
        this.blockState = pBlockState;
    }

    public StructureMessage(FriendlyByteBuf pBuffer) {
        var pairs = pBuffer.readCollection(Lists::newArrayListWithCapacity, Pair::readFromBuffer);

        this.pos = pairs.stream().map(pair -> pair.pos).toList();
        this.blockState = pairs.stream().map(pair -> pair.blockState).toList();
    }

    public void write(FriendlyByteBuf pBuffer) {
        var collection = new ArrayList<Pair>();

        for (int i = 0; i < pos.size(); i++) {
            collection.add(new Pair(pos.get(i), blockState.get(i)));
        }

        pBuffer.writeCollection(collection, Pair::writeToBuffer);
    }

    public void handle(CustomPayloadEvent.Context context) {
        for (int i = 0; i < pos.size(); i++) {
            GhostBlockParams params = new GhostBlockParams(blockState.get(i), pos.get(i));
            CityPlannerClient.GHOST_BLOCKS.add(new GhostBlocks.Entry(params));
        }
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