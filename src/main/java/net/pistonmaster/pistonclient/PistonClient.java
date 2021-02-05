package net.pistonmaster.pistonclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;
import net.pistonmaster.pistonclient.cache.ServerMaxCache;
import net.pistonmaster.pistonclient.discord.MessageTool;
import net.pistonmaster.pistonclient.listeners.DisconnectListener;
import net.pistonmaster.pistonclient.listeners.JoinListener;
import net.pistonmaster.pistonclient.mixin.ServerEntryAccessor;
import net.pistonmaster.pistonclient.mixin.ServerScreenAccessor;
import net.pistonmaster.pistonclient.mixin.WidgetAccessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@Environment(EnvType.CLIENT)
public class PistonClient implements ClientModInitializer {
    public static final Logger logger = LogManager.getLogger("PistonClient");

    @Override
    public void onInitializeClient() {
        MinecraftClient.getInstance().getGame().onStartGameSession();
        logger.info("Starting PistonClient!");

        logger.info("Loading discord rpc!");
        MessageTool.init();
        MessageTool.setStatus("Nice little client.", "deving");

        ClientPlayConnectionEvents.JOIN.register(new JoinListener());

        ClientPlayConnectionEvents.DISCONNECT.register(new DisconnectListener());

        final Thread serverlistCrawlerThread = new Thread(() -> new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (MinecraftClient.getInstance().currentScreen instanceof MultiplayerScreen) {
                    List<MultiplayerServerListWidget.ServerEntry> list = ((WidgetAccessor) ((ServerScreenAccessor) MinecraftClient.getInstance().currentScreen).getWidget()).getServers();

                    for (MultiplayerServerListWidget.ServerEntry entry : list) {
                        ServerInfo info = ((ServerEntryAccessor) entry).getServer();

                        if (info.online && !info.playerCountLabel.asString().isEmpty()) {
                            ServerMaxCache.get().put(info.address, Integer.parseInt(info.playerCountLabel.getSiblings().get(1).asString()));
                        }
                    }
                }
            }
        }, 0, TimeUnit.SECONDS.toMillis(1)));

        serverlistCrawlerThread.setName("Serverlist crawler thread");

        serverlistCrawlerThread.start();
    }
}
