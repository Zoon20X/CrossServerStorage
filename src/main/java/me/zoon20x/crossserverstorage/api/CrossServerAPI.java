package me.zoon20x.crossserverstorage.api;

import me.zoon20x.crossserverstorage.spigot.CrossServerStorage;

public class CrossServerAPI {
    public static NetworkAPI getNetworkAPI() {
        return CrossServerStorage.getInstance().getSocketSend();
    }
}
