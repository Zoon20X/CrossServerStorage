package me.zoon20x.crossserverstorage.networkUtils;

import java.io.Serializable;
import java.util.UUID;

public class ProxyLeaveData implements Serializable {

    private final String server;
    private final UUID player;

    public ProxyLeaveData(String server, UUID player) {
        this.server = server;
        this.player = player;
    }


    public String getServer() {
        return server;
    }

    public UUID getPlayer() {
        return player;
    }
}
