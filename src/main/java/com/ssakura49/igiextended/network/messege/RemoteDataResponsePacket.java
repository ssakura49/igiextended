package com.ssakura49.igiextended.network.messege;

import com.ssakura49.igiextended.IGIExtended;
import com.ssakura49.igiextended.tags.IGIETags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoteDataResponsePacket {
    private final CompoundTag data;

    public RemoteDataResponsePacket(CompoundTag data) {
        this.data = data.copy();
    }

    public static void encode(RemoteDataResponsePacket msg, FriendlyByteBuf buf) {
        buf.writeNbt(msg.data);
    }

    public static RemoteDataResponsePacket decode(FriendlyByteBuf buf) {
        CompoundTag tag = buf.readNbt();
        if (tag == null) tag = new CompoundTag();
        return new RemoteDataResponsePacket(tag);
    }

    public static void handle(RemoteDataResponsePacket msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            if (msg.data != null && msg.data.contains("meanTickTime")) {
                IGIExtended.cachedData = msg.data.copy();
                IGIETags.lastRemoteUpdate = System.currentTimeMillis();
            }
        });
        context.setPacketHandled(true);
    }
}
