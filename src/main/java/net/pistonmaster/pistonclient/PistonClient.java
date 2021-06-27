package net.pistonmaster.pistonclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.pistonmaster.pistonclient.cache.ServerMaxCache;
import net.pistonmaster.pistonclient.discord.MessageTool;
import net.pistonmaster.pistonclient.gui.SettingsGUI;
import net.pistonmaster.pistonclient.gui.SettingsScreen;
import net.pistonmaster.pistonclient.listeners.JoinListener;
import net.pistonmaster.pistonclient.mixin.ServerEntryAccessor;
import net.pistonmaster.pistonclient.mixin.ServerScreenAccessor;
import net.pistonmaster.pistonclient.mixin.WidgetAccessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.time.Instant;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@Environment(EnvType.CLIENT)
public class PistonClient implements ClientModInitializer {
    public static final Logger logger = LogManager.getLogger("PistonClient");
    public static final Instant clientStart = Instant.now();
    private KeyBinding keyBinding;

    @Override
    public void onInitializeClient() {
        MinecraftClient.getInstance().getGame().onStartGameSession();
        logger.info("Starting PistonClient!");

        logger.info("Loading discord rpc!");
        MessageTool.init();
        MessageTool.setStatus("Nice little client.", "deving");

        ClientPlayConnectionEvents.JOIN.register(new JoinListener());

        final var serverlistCrawlerThread = new Thread(() -> new Timer().scheduleAtFixedRate(new TimerTask() {
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

        logger.info("Initializing modules");
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.pistonclient.gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                "key.categories.ui"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                MinecraftClient.getInstance().openScreen(new SettingsScreen(new SettingsGUI()));
            }
        });
    }
}
