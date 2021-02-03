package net.pistonmaster.pistonclient.listeners;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.pistonmaster.pistonclient.PistonClient;

public class JoinListener implements ClientPlayConnectionEvents.Join {
    @Override
    public void onPlayReady(ClientPlayNetworkHandler handler, PacketSender sender, MinecraftClient client) {
        PistonClient.logger.info(handler.getConnection().getAddress().toString().split("/")[0]);
    }
}
