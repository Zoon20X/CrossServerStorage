package me.zoon20x.crossserverstorage.spigot;


import me.zoon20x.crossserverstorage.networkUtils.BasicObject;
import me.zoon20x.crossserverstorage.networkUtils.SendDataOverNetwork;
import me.zoon20x.crossserverstorage.networkUtils.SerializeData;
import me.zoon20x.crossserverstorage.networkUtils.ServerSocketUtils;
import me.zoon20x.crossserverstorage.spigot.socket.SocketUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public final class CrossServerStorage extends JavaPlugin {
    private static CrossServerStorage instance;
    private SocketUtils socketUtils;
    private ServerSocketUtils serverSocketUtils;
    private Socket socket;



    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        this.serverSocketUtils = new ServerSocketUtils(3001);
        this.socketUtils = new SocketUtils();
        try {
            socket = new Socket("127.0.0.1", 3000);
            DataOutputStream o = new DataOutputStream(socket.getOutputStream());
            BasicObject bo = new BasicObject();
            String b = SerializeData.toString(bo);
            SendDataOverNetwork over = new SendDataOverNetwork("Testing", b);
            String send = SerializeData.toString(over);
            o.writeUTF(send);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
