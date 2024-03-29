package net.pistonmaster.pistonclient.discord;

import com.google.gson.Gson;
import de.jcm.discordgamesdk.DiscordEventAdapter;
import de.jcm.discordgamesdk.activity.ActivityJoinRequestReply;
import de.jcm.discordgamesdk.user.DiscordUser;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.MinecraftClient;
import net.pistonmaster.pistonclient.PistonClient;
import net.pistonmaster.pistonclient.data.ServerJoinRequest;
import net.pistonmaster.pistonclient.screens.JoinWarningScreen;

@RequiredArgsConstructor
public class EventAdapter extends DiscordEventAdapter {
    private final MessageTool messageTool;

    @Override
    public void onActivityJoin(String secret) {
        PistonClient.logger.info("Joined activity with secret " + secret);
        ServerJoinRequest request = new Gson().fromJson(secret, ServerJoinRequest.class);
        MinecraftClient client = MinecraftClient.getInstance();

        MinecraftClient.getInstance().execute(() -> {
            client.setScreen(new JoinWarningScreen(client.currentScreen, request));
        });

        messageTool.core.lobbyManager().connectLobbyWithActivitySecret(secret, lobby -> {
            messageTool.core.activityManager();
        });
    }

    @Override
    public void onActivityJoinRequest(DiscordUser user) {
        messageTool.core.activityManager().sendRequestReply(user.getUserId(), ActivityJoinRequestReply.YES);
        PistonClient.logger.info(user.getUsername() + "#" + user.getDiscriminator() + (user.isBot() ? " joined you and is a bot!" : " joined you!"));
    }
}
