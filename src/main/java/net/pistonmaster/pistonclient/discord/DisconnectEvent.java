package net.pistonmaster.pistonclient.discord;

import net.arikia.dev.drpc.callbacks.DisconnectedCallback;
import net.pistonmaster.pistonclient.PistonClient;

public class DisconnectEvent implements DisconnectedCallback {
    @Override
    public void apply(int errorCode, String message) {
        PistonClient.logger.info("Disconnected from discord! Code: " + errorCode + " Message: " + message);
    }
}
