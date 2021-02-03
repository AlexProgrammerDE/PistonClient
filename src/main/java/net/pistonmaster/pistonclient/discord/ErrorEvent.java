package net.pistonmaster.pistonclient.discord;

import net.arikia.dev.drpc.callbacks.ErroredCallback;
import net.pistonmaster.pistonclient.PistonClient;

public class ErrorEvent implements ErroredCallback {
    @Override
    public void apply(int errorCode, String message) {
        PistonClient.logger.info("Uh oh! Error with discord. Code: " + errorCode + " Message: " + message);
    }
}
