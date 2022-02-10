package net.pistonmaster.pistonclient.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ServerJoinRequest {
    private String server;
    private int port;

    public String format() {
        return String.format("%s:%d", server, port);
    }
}
