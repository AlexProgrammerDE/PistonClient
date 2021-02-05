package net.pistonmaster.pistonclient.discord;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;
import net.pistonmaster.pistonclient.PistonClient;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

public class MessageTool {
    private static Core usedCore = null;

    private MessageTool() {}

    public static void setStatus(String detail, String state) {
        if (usedCore == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Create the Activity
        try (Activity activity = new Activity()) {
            activity.setDetails(detail);
            activity.setState(state);

            // Setting a start time causes an "elapsed" field to appear
            activity.timestamps().setStart(PistonClient.clientStart);

            // Make a "cool" image show up
            activity.assets().setLargeImage("default");

            // Finally, update the current activity to our activity
            usedCore.activityManager().updateActivity(activity);
        }
    }

    public static void setParty(String detail, String state, int people, int max, String serverAddress, Instant join) {
        if (usedCore == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Create the Activity
        try (Activity activity = new Activity()) {
            activity.setDetails(detail);
            activity.setState(state);

            // Setting a start time causes an "elapsed" field to appear
            activity.timestamps().setStart(join);

            // We are in a party with 10 out of 100 people.
            activity.party().size().setMaxSize(max);
            activity.party().size().setCurrentSize(people);

            // Make a "cool" image show up
            activity.assets().setLargeImage("default");

            // Setting a join secret and a party ID causes an "Ask to Join" button to appear
            activity.party().setID(UUID.randomUUID().toString());
            activity.secrets().setJoinSecret(serverAddress);

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

                    params.registerEventHandler(new EventAdapter());

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
