package net.pistonmaster.pistonclient.listeners;

import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.status.ServerStatusInfo;
import com.github.steveice10.mc.protocol.data.status.handler.ServerInfoHandler;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.*;
import com.github.steveice10.packetlib.packet.Packet;
import com.github.steveice10.packetlib.tcp.TcpClientSession;
import com.google.common.net.InternetDomainName;
import lombok.RequiredArgsConstructor;
import net.earthcomputer.multiconnect.api.MultiConnectAPI;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.pistonmaster.pistonclient.PistonClient;
import net.pistonmaster.pistonclient.data.ServerJoinRequest;
import org.apache.commons.validator.routines.DomainValidator;

import java.net.InetSocketAddress;
import java.time.Instant;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class JoinListener implements ClientPlayConnectionEvents.Join {
    private static final String[] domainOptions = {"connect.", "play.", "mc.", ""};
    private final Map<String, ServerStatusInfo> maxMap = new ConcurrentHashMap<>();
    private Timer updateTimer;
    private final PistonClient pistonClient;

    private static CompletableFuture<ServerStatusInfo> pingServer(String serverHost, int port) {
        CompletableFuture<ServerStatusInfo> future = new CompletableFuture<>();

        MinecraftProtocol protocol = new MinecraftProtocol();
        Session client = new TcpClientSession(serverHost, port, protocol);
        client.setFlag(MinecraftConstants.SERVER_INFO_HANDLER_KEY, (ServerInfoHandler) (session, info) -> {
            future.complete(info);
        });

        client.addListener(new SessionListener() {
            @Override
            public void packetError(PacketErrorEvent event) {
                if (!future.isDone()) future.completeExceptionally(event.getCause());
            }

            @Override
            public void disconnected(DisconnectedEvent event) {
                if (!future.isDone()) future.completeExceptionally(event.getCause());
            }

            @Override
            public void connected(ConnectedEvent event) {
            }

            @Override
            public void disconnecting(DisconnectingEvent event) {
            }

            @Override
            public void packetReceived(Session session, Packet packet) {
            }

            @Override
            public void packetSending(PacketSendingEvent event) {
            }

            @Override
            public void packetSent(Session session, Packet packet) {
            }
        });

        client.connect();

        return future;
    }

    @Override
    public void onPlayReady(ClientPlayNetworkHandler handler, PacketSender sender, MinecraftClient client) {
        if (!(handler.getConnection().getAddress() instanceof InetSocketAddress address)) return;

        if (updateTimer != null) {
            updateTimer.cancel();
        }

        Instant joinTime = Instant.now();

        updateTimer = new Timer();

        updateTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (handler.getConnection().isOpen()) {
                    String serverName = address.getHostString().split("/")[0];

                    if (isPublic(serverName)) {
                        if (!maxMap.containsKey(serverName)) {
                            maxMap.put(serverName, pingServer(serverName, address.getPort()).join());
                        }

                        ServerStatusInfo info = maxMap.get(serverName);
                        ServerJoinRequest request = new ServerJoinRequest(serverName, address.getPort(), MultiConnectAPI.instance().getProtocolVersion());

                        pistonClient.getMessageTool().setParty(
                                "Nice little client.",
                                replaceAll(serverName),
                                info.getPlayerInfo().getOnlinePlayers(),
                                info.getPlayerInfo().getMaxPlayers(),
                                request,
                                joinTime
                        );
                    } else {
                        pistonClient.getMessageTool().setStatus("Nice little client.", "deving");
                        cancel();
                    }
                } else {
                    pistonClient.getMessageTool().setStatus("Nice little client.", "deving");
                    cancel();
                }
            }
        }, 20, TimeUnit.SECONDS.toMillis(1));
    }

    @SuppressWarnings("UnstableApiUsage")
    private boolean isPublic(String domain) {
        if (DomainValidator.getInstance().isValid(domain)) {
            InternetDomainName publicSuffix = InternetDomainName.from(domain).publicSuffix();

            if (publicSuffix != null) {
                String withoutPublicSuffix = domain.replace(publicSuffix.toString(), "");
                return startsWithArray(domain) || !withoutPublicSuffix.contains(".");
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean startsWithArray(String str) {
        for (String option : domainOptions) {
            if (str.startsWith(option))
                return true;
        }

        return false;
    }

    public static String replaceAll(String str) {
        for (String option : domainOptions) {
            str = str.replace(option, "");
        }

        if (str.endsWith("."))
            str = str.substring(0, str.length() - 1);

        return str;
    }
}
