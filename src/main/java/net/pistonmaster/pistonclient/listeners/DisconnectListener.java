package net.pistonmaster.pistonclient.listeners;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.pistonmaster.pistonclient.discord.MessageTool;

public class DisconnectListener {
    public void onPlayDisconnect(ClientPlayNetworkHandler handler, MinecraftClient client) {
        System.out.println(1);
        if (JoinListener.serverUpdateThread != null) {
            JoinListener.serverUpdateThread.interrupt();
        }

        JoinListener.shouldTick = false;

        new Thread(() -> {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MessageTool.setStatus("Nice little client.", "deving");
        }).start();
    }
}
