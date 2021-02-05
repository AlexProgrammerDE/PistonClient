package net.pistonmaster.pistonclient.listeners;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.pistonmaster.pistonclient.discord.MessageTool;

public class DisconnectListener implements ClientPlayConnectionEvents.Disconnect {
    @Override
    public void onPlayDisconnect(ClientPlayNetworkHandler handler, MinecraftClient client) {
        MessageTool.setStatus("Nice little client.", "deving");
    }
}
