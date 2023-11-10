package me.zoon20x.crossserverstorage.bungee;

import me.zoon20x.crossserverstorage.bungee.socket.SocketUtils;
import me.zoon20x.crossserverstorage.networkUtils.ServerSocketUtils;
import net.md_5.bungee.api.plugin.Plugin;

public final class CrossServerStorage extends Plugin {
    private static CrossServerStorage instance;
    private SocketUtils socketUtils;
    private ServerSocketUtils serverSocketUtils;


    private int serverPort;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        this.serverSocketUtils = new ServerSocketUtils(3001);
        this.socketUtils = new SocketUtils();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        serverSocketUtils.close();
    }







    public static CrossServerStorage getInstance(){
        return instance;
    }

    public SocketUtils getSocketUtils() {
        return socketUtils;
    }

    public ServerSocketUtils getServerSocketUtils() {
        return serverSocketUtils;
    }
}
