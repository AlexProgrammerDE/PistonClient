package net.pistonmaster.pistonclient.listeners;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.pistonmaster.pistonclient.cache.ServerMaxCache;
import net.pistonmaster.pistonclient.discord.MessageTool;

import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class JoinListener implements ClientPlayConnectionEvents.Join {
    protected static Thread serverUpdateThread = null;
    protected static boolean shouldTick = false;

    @Override
    public void onPlayReady(ClientPlayNetworkHandler handler, PacketSender sender, MinecraftClient client) {
        Instant joinTime = Instant.now();
        shouldTick = true;

        serverUpdateThread = new Thread(() -> new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String serverName = handler.getConnection().getAddress().toString().split("/")[0];

                if (shouldTick)
                    MessageTool.setParty("Nice little client.", serverName, normalize(handler.getPlayerUuids().size()), normalize(ServerMaxCache.get().get(serverName)), serverName, joinTime);
            }
        }, 20, TimeUnit.SECONDS.toMillis(1)));

        serverUpdateThread.setName("Server update thread");

        serverUpdateThread.start();
    }

    private int normalize(Integer i) {
        if (i == null)
            i = 1;

        if (i < 1)
            i = 1;

        return i;
    }
}
