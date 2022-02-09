package net.pistonmaster.pistonclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.pistonmaster.pistonclient.discord.MessageTool;
import net.pistonmaster.pistonclient.gui.SettingsGUI;
import net.pistonmaster.pistonclient.gui.SettingsScreen;
import net.pistonmaster.pistonclient.listeners.JoinListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import java.time.Instant;

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

        logger.info("Initializing modules");
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.pistonclient.gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                "key.categories.ui"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                MinecraftClient.getInstance().setScreen(new SettingsScreen(new SettingsGUI()));
            }
        });
    }
}
