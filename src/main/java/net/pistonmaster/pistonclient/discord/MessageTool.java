package net.pistonmaster.pistonclient.discord;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerWarningScreen;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

public class MessageTool {
    private static Core usedCore = null;

    public static void setStatus(String detail, String state) {
        // Create the Activity
        try (Activity activity = new Activity()) {
            activity.setDetails("Running an example");
            activity.setState("and having fun");

            // Setting a start time causes an "elapsed" field to appear
            activity.timestamps().setStart(Instant.now());

            // We are in a party with 10 out of 100 people.
            activity.party().size().setMaxSize(100);
            activity.party().size().setCurrentSize(10);

            // Make a "cool" image show up
            activity.assets().setLargeImage("test");

            // Setting a join secret and a party ID causes an "Ask to Join" button to appear
            activity.party().setID("Party!");
            activity.secrets().setJoinSecret("Join!");

            // Finally, update the current activity to our activity
            usedCore.activityManager().updateActivity(activity);
        }
    }

    public static void init() {
        try {
            File discordLibrary = DownloadNativeLibrary.downloadDiscordLibrary();

            if (discordLibrary == null) {
                System.err.println("Error downloading Discord SDK.");
                System.exit(-1);
            }

            // Initialize the Core
            Core.init(discordLibrary);

            new Thread(() -> {
                // Set parameters for the Core
                try (CreateParams params = new CreateParams()) {
                    params.setClientID(806528088812945419L);
                    params.setFlags(CreateParams.getDefaultFlags());
                    // Create the Core
                    try (Core core = new Core(params)) {
                        usedCore = core;
                        while (true) {
                            core.runCallbacks();
                        }
                    }
                }

            }).start();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error downloading Discord SDK.");
            System.exit(-1);
        }
    }
}
