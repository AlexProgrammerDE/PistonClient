package net.pistonmaster.pistonclient.listeners;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;

public class DisconnectListener implements ClientPlayConnectionEvents.Disconnect {
    @Override
    public void onPlayDisconnect(ClientPlayNetworkHandler handler, MinecraftClient client) {
        // DiscordRPC.discordUpdatePresence(new DiscordRichPresence.Builder("Currently developing a mod.").setDetails("Nice little client.").setBigImage("default", "PistonClient").build());
    }
}
