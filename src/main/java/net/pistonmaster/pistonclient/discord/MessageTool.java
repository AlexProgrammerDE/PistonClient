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
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MessageTool {
    protected Core core = null;
    @Getter
    @Setter
    private volatile boolean activated = true;
    private static final long CLIENT_ID = 806528088812945419L;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public void setStatus(String detail, String state) {
        if (core == null)
            return;

        // Create the Activity
        try (Activity activity = new Activity()) {
            activity.setDetails(detail);
            activity.setState(state);

            // Setting a start time causes an "elapsed" field to appear
            activity.timestamps().setStart(PistonClient.clientStart);

            fillData(activity);

            // Finally, update the current activity to our activity
            core.activityManager().updateActivity(activity);
        }
    }

    public void setParty(String detail, String serverName, int people, int max, ServerJoinRequest request, Instant join) {
        if (core == null)
            return;

        // Create the Activity
        try (Activity activity = new Activity()) {
            activity.setDetails(detail);
            activity.setState(serverName);

            activity.timestamps().setStart(join);

            activity.party().size().setMaxSize(max);
            activity.party().size().setCurrentSize(people);

            fillData(activity);

            // Setting a join secret and a party ID causes an "Ask to Join" button to appear
            activity.party().setID(UUID.randomUUID().toString());
            activity.secrets().setJoinSecret(new Gson().toJson(request));

            // Finally, update the current activity to our activity
            core.activityManager().updateActivity(activity);
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
                params.registerEventHandler(new EventAdapter(this));
                params.setFlags(CreateParams.getDefaultFlags());
                core = new Core(params);

                executor.scheduleAtFixedRate(() -> {
                    if (!isActivated())
                        return;

                    core.runCallbacks();
                }, 0, 16, TimeUnit.MILLISECONDS);
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error downloading Discord SDK.");
            System.exit(-1);
        }
    }

    protected void generateServerPartyActivity(Activity activity, ServerJoinRequest request, Instant join) {
        activity.assets().setLargeImage("default");
        activity.setDetails("Server");
        activity.setState("Server");
        activity.timestamps().setStart(Instant.now());
        activity.party().size().setMaxSize(1);

        activity.party().setID("server!");
    }

    private void fillData(Activity activity) {
        activity.assets().setLargeImage("default");
        activity.assets().setLargeText("PistonClient");
    }
}
