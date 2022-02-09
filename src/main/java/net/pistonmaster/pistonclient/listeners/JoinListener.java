package net.pistonmaster.pistonclient.listeners;

import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.status.ServerStatusInfo;
import com.github.steveice10.mc.protocol.data.status.handler.ServerInfoHandler;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.*;
import com.github.steveice10.packetlib.packet.Packet;
import com.github.steveice10.packetlib.tcp.TcpClientSession;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.pistonmaster.pistonclient.discord.MessageTool;
import org.apache.commons.validator.routines.DomainValidator;

import java.net.InetSocketAddress;
import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class JoinListener implements ClientPlayConnectionEvents.Join {
    private static final String[] domainOptions = {"connect.", "play.", "mc."};
    private final Map<String, ServerStatusInfo> maxMap = new ConcurrentHashMap<>();
    private Thread serverUpdateThread = null;

    private static CompletableFuture<ServerStatusInfo> pingServer() {
        CompletableFuture<ServerStatusInfo> future = new CompletableFuture<>();

        MinecraftProtocol protocol = new MinecraftProtocol();
        Session client = new TcpClientSession("6b6t.org", 25565, protocol);
        client.setFlag(MinecraftConstants.SERVER_INFO_HANDLER_KEY, (ServerInfoHandler) (session, info) -> {
            future.complete(info);
            System.out.println("Version: " + info.getVersionInfo().getVersionName()
                    + ", " + info.getVersionInfo().getProtocolVersion());
            System.out.println("Player Count: " + info.getPlayerInfo().getOnlinePlayers()
                    + " / " + info.getPlayerInfo().getMaxPlayers());
            System.out.println("Players: " + Arrays.toString(info.getPlayerInfo().getPlayers()));
            System.out.println("Description: " + info.getDescription());
            System.out.println("Icon: " + Arrays.toString(info.getIconPng()));
        });


        client.addListener(new SessionListener() {
            @Override
            public void packetReceived(Session session, Packet packet) {
            }

            @Override
            public void packetSending(PacketSendingEvent event) {
            }

            @Override
            public void packetSent(Session session, Packet packet) {
            }

            @Override
            public void packetError(PacketErrorEvent event) {
                if (!future.isDone()) future.completeExceptionally(event.getCause());
            }

            @Override
            public void connected(ConnectedEvent event) {
            }

            @Override
            public void disconnecting(DisconnectingEvent event) {
            }

            @Override
            public void disconnected(DisconnectedEvent event) {
                if (!future.isDone()) future.completeExceptionally(event.getCause());
            }
        });
        client.connect();

        return future;
    }

    @Override
    public void onPlayReady(ClientPlayNetworkHandler handler, PacketSender sender, MinecraftClient client) {
        if (!(handler.getConnection().getAddress() instanceof InetSocketAddress)) return;

        if (serverUpdateThread != null) {
            serverUpdateThread.stop();
        }

        Instant joinTime = Instant.now();

        serverUpdateThread = new Thread(() -> {
            Timer timer = new Timer();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (handler.getConnection().isOpen()) {
                        String serverName = ((InetSocketAddress) handler.getConnection().getAddress()).getHostString().split("/")[0];

                        if (isPublic(serverName)) {
                            if (!maxMap.containsKey(serverName)) {
                                maxMap.put(serverName, pingServer().join());
                            }

                            ServerStatusInfo info = maxMap.get(serverName);

                            MessageTool.setParty("Nice little client.", replaceAll(serverName), normalize(handler.getPlayerUuids().size()), info.getPlayerInfo().getMaxPlayers(), serverName, joinTime);
                        }
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

    private boolean isPublic(String domain) {
        return DomainValidator.getInstance().isValid(domain) && startsWithArray(domain);
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
