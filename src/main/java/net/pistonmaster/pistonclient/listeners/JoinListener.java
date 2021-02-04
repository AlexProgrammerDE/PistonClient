package net.pistonmaster.pistonclient.listeners;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class JoinListener implements ClientPlayConnectionEvents.Join {
    @Override
    public void onPlayReady(ClientPlayNetworkHandler handler, PacketSender sender, MinecraftClient client) {
        final Thread serverUpdateThread = new Thread(() -> new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                String serverName = handler.getConnection().getAddress().toString().split("/")[0];

                /*

                DiscordRichPresence.Builder rich = new DiscordRichPresence.Builder(serverName).setDetails("Nice little mod.").setBigImage("default", "PistonClient");

                rich.setParty(serverName, handler.getPlayerUuids().size(), ServerMaxCache.get().get(serverName));

                rich.setSecrets(Base64.encode(serverName.getBytes()), Base64.encode(serverName.getBytes()));

                DiscordRPC.discordUpdatePresence(rich.build());

                 */
            }
        }, 0, TimeUnit.SECONDS.toMillis(1)));

        serverUpdateThread.setName("Server update thread");

        serverUpdateThread.start();
    }
}
