package net.pistonmaster.pistonclient.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.client.network.ServerInfo;

@Getter
@AllArgsConstructor
public class ServerJoinRequest {
    private String server;
    private int port;
    private int protocol;

    public ServerInfo toServerInfo() {
        return new ServerInfo("server", format(), false);
    }

    public String format() {
        return String.format("%s:%d", server, port);
    }
}
