package net.pistonmaster.pistonclient;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class PistonClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        setupDiscord();
    }

    public void setupDiscord() {
        DiscordRPC.discordInitialize("806528088812945419",
                new DiscordEventHandlers.Builder().setReadyEventHandler((user) ->
                System.out.println("Welcome " + user.username + "#" + user.discriminator + "!")).build(), true);
    }
}
