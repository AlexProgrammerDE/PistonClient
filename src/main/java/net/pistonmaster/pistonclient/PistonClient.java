package net.pistonmaster.pistonclient;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.pistonmaster.pistonclient.discord.DisconnectEvent;
import net.pistonmaster.pistonclient.discord.ErrorEvent;
import net.pistonmaster.pistonclient.discord.ReadyEvent;
import net.pistonmaster.pistonclient.listeners.JoinListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
        DiscordRPC.discordInitialize("806528088812945419",
                new DiscordEventHandlers.Builder()
                        .setReadyEventHandler(new ReadyEvent())
                        .setErroredEventHandler(new ErrorEvent())
                        .setDisconnectedEventHandler(new DisconnectEvent()).build(), true);

        final Thread callbackThread = new Thread(() -> new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                DiscordRPC.discordRunCallbacks();
            }
        }, 0, TimeUnit.SECONDS.toMillis(1)));

        callbackThread.setName("Discord callback thread");

        callbackThread.start();

        DiscordRichPresence.Builder rich = new DiscordRichPresence.Builder("Currently developing a mod.").setDetails("Nice little client.");

        // rich.setSecrets("10b10t.org", "10b10t.org");

        rich.setParty("10b10t.org", 1, 2020);

        DiscordRPC.discordUpdatePresence(rich.build());

        ClientPlayConnectionEvents.JOIN.register(new JoinListener());
    }
}
