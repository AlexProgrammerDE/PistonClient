package net.pistonmaster.pistonclient.discord;

import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.pistonmaster.pistonclient.PistonClient;

public class ReadyEvent implements ReadyCallback {
    @Override
    public void apply(DiscordUser user) {
        PistonClient.logger.info(user.username + "#" + user.discriminator + " logged in!");
    }
}
