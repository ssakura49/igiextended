package com.ssakura49.igiextended.tags;

import com.ssakura49.igiextended.IGIExtended;
import com.ssakura49.igiextended.network.PackHandler;
import com.ssakura49.igiextended.network.messege.RemoteDataPacket;
import net.minecraft.server.MinecraftServer;

import static com.ssakura49.igiextended.network.messege.RemoteDataPacket.mean;

public class TPS extends IGIETags{
    @Override
    public String getValue() {
        try {
            if (world.isClientSide) {
                long delay = (System.currentTimeMillis() - lastRemoteUpdate);
                if (delay > 1500 || delay < 0) {
                    PackHandler.INSTANCE.sendToServer(new RemoteDataPacket());
                    lastRemoteUpdate = System.currentTimeMillis();
                }
                return String.format("%.2f", IGIExtended.cachedData.getFloat("meanTPS"));
            } else {
                MinecraftServer server = world.getServer();
                if (server != null) {
                    long[] times = server.getTickTime(world.dimension());
                    if (times != null && times.length > 0) {
                        double meanTickTime = mean(times) * 1.0E-6D;
                        double meanTPS = Math.min(1000.0 / meanTickTime, 20);
                        return String.format("%.2f", meanTPS);
                    }
                }
            }
        } catch (Throwable e) {
            IGIExtended.LOGGER.error("Error When Get Tag Data: TPS.", e);
        }
        return "-1";
    }
}
