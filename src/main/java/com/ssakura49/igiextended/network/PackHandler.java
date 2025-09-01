package com.ssakura49.igiextended.network;

import com.ssakura49.igiextended.IGIExtended;
import com.ssakura49.igiextended.network.messege.RemoteDataPacket;
import com.ssakura49.igiextended.network.messege.RemoteDataResponsePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PackHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
            .named(ResourceLocation.fromNamespaceAndPath(IGIExtended.MODID, "main"))
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .simpleChannel();
    private static int id = 0;

    public static void init() {
        INSTANCE.registerMessage(id++, RemoteDataPacket.class, RemoteDataPacket::encode, RemoteDataPacket::decode, RemoteDataPacket::handle);
        INSTANCE.registerMessage(id++, RemoteDataResponsePacket.class,RemoteDataResponsePacket::encode,RemoteDataResponsePacket::decode,RemoteDataResponsePacket::handle);
    }

    public static <MSG> void sendToServer(MSG msg){
        INSTANCE.sendToServer(msg);
    }

    public static <MSG> void sendToClient(ServerPlayer player, MSG msg){
        INSTANCE.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static <MSG> void sendToAll(MSG msg) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }

    public static <MSG> void sendToTrackingChunk(LevelChunk chunk, MSG msg) {
        INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), msg);
    }

    public static <MSG> void sendToTrackingEntity(Entity entity, MSG msg) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), msg);
    }

    public static <MSG> void sendToTrackingEntityAndPlayer(Entity entity, MSG msg) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), msg);
    }
}
