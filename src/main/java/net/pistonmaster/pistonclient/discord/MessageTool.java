package net.pistonmaster.pistonclient.discord;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;
import net.pistonmaster.pistonclient.PistonClient;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MessageTool {
    protected static Core usedCore = null;

    private MessageTool() {
    }

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

            activity.timestamps().setStart(join);

            activity.party().size().setMaxSize(max);
            activity.party().size().setCurrentSize(people);

            activity.assets().setLargeImage("default");

            // Setting a join secret and a party ID causes an "Ask to Join" button to appear
            activity.party().setID("server!");
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
                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                CreateParams params = new CreateParams();
                params.setClientID(806528088812945419L);
                params.registerEventHandler(new EventAdapter());
                params.setFlags(CreateParams.getDefaultFlags());
                usedCore = new Core(params);

                executor.scheduleAtFixedRate(usedCore::runCallbacks, 0, 16, TimeUnit.MILLISECONDS);
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error downloading Discord SDK.");
            System.exit(-1);
        }
    }
}
