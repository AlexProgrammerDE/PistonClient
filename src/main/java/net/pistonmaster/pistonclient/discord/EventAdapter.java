package net.pistonmaster.pistonclient.discord;

import com.google.gson.Gson;
import de.jcm.discordgamesdk.DiscordEventAdapter;
import de.jcm.discordgamesdk.user.DiscordUser;
import net.minecraft.client.MinecraftClient;
import net.pistonmaster.pistonclient.PistonClient;
import net.pistonmaster.pistonclient.data.ServerJoinRequest;
import net.pistonmaster.pistonclient.screens.JoinWarningScreen;

public class EventAdapter extends DiscordEventAdapter {
    @Override
    public void onActivityJoin(String secret) {
        ServerJoinRequest request = new Gson().fromJson(secret, ServerJoinRequest.class);

        MinecraftClient.getInstance().setScreen(new JoinWarningScreen(MinecraftClient.getInstance().currentScreen, request));
    }

    @Override
    public void onActivityJoinRequest(DiscordUser user) {
        PistonClient.logger.info(user.getUsername() + "#" + user.getDiscriminator() + (user.isBot() ? " joined you and is a bot!" : " joined you!"));
    }
}
