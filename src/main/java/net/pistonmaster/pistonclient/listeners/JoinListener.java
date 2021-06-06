package net.pistonmaster.pistonclient.listeners;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.pistonmaster.pistonclient.cache.ServerMaxCache;
import net.pistonmaster.pistonclient.discord.MessageTool;
import org.apache.commons.validator.routines.DomainValidator;

import java.net.InetSocketAddress;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class JoinListener implements ClientPlayConnectionEvents.Join {
    private static final String[] domainOptions = {"connect.", "play.", "mc."};
    private Thread serverUpdateThread = null;

    @Override
    public void onPlayReady(ClientPlayNetworkHandler handler, PacketSender sender, MinecraftClient client) {
        if (!(handler.getConnection().getAddress() instanceof InetSocketAddress)) return;

        if (serverUpdateThread != null) {
            serverUpdateThread.stop();
        }

        Instant joinTime = Instant.now();

        serverUpdateThread = new Thread(() -> {
            Timer timer = new Timer();

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (handler.getConnection().isOpen()) {
                        String serverName = normalizeName(((InetSocketAddress) handler.getConnection().getAddress()).getHostString().split("/")[0]);

                        if (serverName.endsWith(".")) {
                            serverName = serverName.substring(0, serverName.length() - 1);
                        }

                        MessageTool.setParty("Nice little client.", replaceAll(serverName), normalize(handler.getPlayerUuids().size()), normalize(getTillWorks(serverName)), serverName, joinTime);
                    } else {
                        MessageTool.setStatus("Nice little client.", "deving");
                        timer.cancel();
                        serverUpdateThread.stop();
                    }
                }
            }, 20, TimeUnit.SECONDS.toMillis(1));
        });

        serverUpdateThread.setName("Server update thread");

        serverUpdateThread.start();
    }

    private int normalize(Integer i) {
        if (i == null)
            i = 1;

        if (i < 1)
            i = 1;

        return i;
    }

    private Integer getTillWorks(String domain) {
        Integer firstTry = ServerMaxCache.get().get(domain);

        if (firstTry != null)
            return firstTry;

        domain = replaceAll(domain);

        Integer secondTry = ServerMaxCache.get().get(domain);

        if (secondTry != null)
            return secondTry;

        for (String str : domainOptions) {
            Integer nextTry = ServerMaxCache.get().get(str + domain);

            if (nextTry != null) {
                return nextTry;
            }
        }

        return null;
    }

    private String normalizeName(String domain) {
        if (DomainValidator.getInstance().isValid(domain)) {
            if (startsWithArray(domain)) {
                return domain;
            } else {
                return "redacted";
            }
        } else {
            return "redacted";
        }
    }

    private boolean startsWithArray(String str) {
        for (String option : domainOptions) {
            if (str.startsWith(option))
                return true;
        }

        return false;
    }

    private String replaceAll(String str) {
        for (String option : domainOptions) {
            str = str.replace(option, "");
        }

        return str;
    }
}
