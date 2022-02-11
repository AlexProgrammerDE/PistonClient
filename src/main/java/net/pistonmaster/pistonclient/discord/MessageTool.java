package net.pistonmaster.pistonclient.discord;

import com.google.gson.Gson;
import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;
import lombok.Getter;
import lombok.Setter;
import net.pistonmaster.pistonclient.PistonClient;
import net.pistonmaster.pistonclient.data.ServerJoinRequest;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MessageTool {
    protected Core usedCore = null;
    @Getter
    @Setter
    private volatile boolean activated = true;
    private static final long CLIENT_ID = 806528088812945419L;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public void setStatus(String detail, String state) {
        if (usedCore == null)
            return;

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

    public void setParty(String detail, String state, int people, int max, ServerJoinRequest request, Instant join) {
        if (usedCore == null)
            return;

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
            activity.secrets().setJoinSecret(new Gson().toJson(request));

            // Finally, update the current activity to our activity
            usedCore.activityManager().updateActivity(activity);
        }
    }

    public void init() {
        try {
            File discordLibrary = DownloadNativeLibrary.downloadDiscordLibrary();

            if (discordLibrary == null) {
                System.err.println("Error downloading Discord SDK.");
                System.exit(-1);
            }

            // Initialize the Core
            Core.init(discordLibrary);

            new Thread(() -> {
                CreateParams params = new CreateParams();
                params.setClientID(CLIENT_ID);
                params.registerEventHandler(new EventAdapter());
                params.setFlags(CreateParams.getDefaultFlags());
                usedCore = new Core(params);

                executor.scheduleAtFixedRate(() -> {
                    if (!isActivated())
                        return;

                    usedCore.runCallbacks();
                }, 0, 16, TimeUnit.MILLISECONDS);
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error downloading Discord SDK.");
            System.exit(-1);
        }
    }
}
