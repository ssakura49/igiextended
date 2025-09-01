package com.ssakura49.igiextended.network.messege;

import com.ssakura49.igiextended.network.PackHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoteDataPacket {
    public RemoteDataPacket() {}

    public static void encode(RemoteDataPacket msg, FriendlyByteBuf buf) {
    }

    public static RemoteDataPacket decode(FriendlyByteBuf buf) {
        return new RemoteDataPacket();
    }

    public static void handle(RemoteDataPacket msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                handle0(player);
            }
        });
        context.setPacketHandled(true);
    }

    private static void handle0(ServerPlayer player) {
        try {
            MinecraftServer server = player.getServer();
            if (server == null) return;
            long[] tickTimes = server.getTickTime(player.level().dimension());
            double meanTickTime = 0;
            if (tickTimes != null) {
                meanTickTime = mean(tickTimes) * 1.0E-6D;
            }
            double meanTPS = (meanTickTime > 0) ? Math.min(1000.0 / meanTickTime, 20) : 20;

            CompoundTag data = new CompoundTag();
            data.putDouble("meanTickTime", meanTickTime);
            data.putDouble("meanTPS", meanTPS);
            PackHandler.sendToClient(player, new RemoteDataResponsePacket(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long mean(long[] arr) {
        if (arr.length == 0) return 0;
        long sum = 0;
        for (long v : arr) sum += v;
        return sum / arr.length;
    }
}
