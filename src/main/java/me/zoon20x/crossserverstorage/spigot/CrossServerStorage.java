package me.zoon20x.crossserverstorage.spigot;


import me.zoon20x.crossserverstorage.networkUtils.BasicObject;
import me.zoon20x.crossserverstorage.networkUtils.SendDataOverNetwork;
import me.zoon20x.crossserverstorage.networkUtils.SerializeData;
import me.zoon20x.crossserverstorage.networkUtils.ServerSocketUtils;
import me.zoon20x.crossserverstorage.spigot.events.NetEvents;
import me.zoon20x.crossserverstorage.spigot.socket.SendTo;
import me.zoon20x.crossserverstorage.spigot.socket.SocketUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public final class CrossServerStorage extends JavaPlugin {
    private static CrossServerStorage instance;
    private SocketUtils socketUtils;
    private ServerSocketUtils serverSocketUtils;


    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();
        String proxyAddress = getConfig().getString("Proxy.address");
        int proxyPort = getConfig().getInt("Proxy.port");
        int networkPort = getConfig().getInt("NetworkPort");


        this.serverSocketUtils = new ServerSocketUtils(networkPort);
        this.socketUtils = new SocketUtils(proxyAddress, proxyPort);
        if(networkPort == 3001){
            socketUtils.sendDataToNetwork(SendTo.ALL, new BasicObject("Lobby"));
        }

        Bukkit.getPluginManager().registerEvents(new NetEvents(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        serverSocketUtils.close();
        socketUtils.getTask().cancel();
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
