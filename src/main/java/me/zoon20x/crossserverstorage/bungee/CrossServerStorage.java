package me.zoon20x.crossserverstorage.bungee;

import me.zoon20x.crossserverstorage.bungee.socket.ServersList;
import me.zoon20x.crossserverstorage.bungee.socket.SocketUtils;
import me.zoon20x.crossserverstorage.networkUtils.SendDataOverNetwork;
import me.zoon20x.crossserverstorage.networkUtils.SerializeData;
import me.zoon20x.crossserverstorage.networkUtils.ServerSocketUtils;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class CrossServerStorage extends Plugin {
    private static CrossServerStorage instance;
    private SocketUtils socketUtils;
    private ServerSocketUtils serverSocketUtils;
    private Socket socket;
    private Configuration configuration;
    private HashMap<String, ServersList> serversLists = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        try {
            makeConfig();
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(CrossServerStorage.getInstance().getDataFolder(), "network.yml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        loadServers();
        this.serverSocketUtils = new ServerSocketUtils(configuration.getInt("NetworkPort"));
        this.socketUtils = new SocketUtils();
    }
    private void loadServers(){
        configuration.getSection("Servers").getKeys().forEach(serverInfo ->{
            System.out.println(serverInfo);
            serversLists.put(serverInfo, new ServersList(serverInfo, configuration.getString("Servers."+serverInfo+".address"), configuration.getInt("Servers."+serverInfo+".port")));
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        serverSocketUtils.close();
        socketUtils.getTask().cancel();
    }

    public void sendDataToServer(SendDataOverNetwork sendDataOverNetwork) {
        if (sendDataOverNetwork.getSendTo().equalsIgnoreCase("SERVER")) {
            try {
                ServersList server = CrossServerStorage.getInstance().getServersLists().get(sendDataOverNetwork.getServerName());
                socket = new Socket(server.getAddress(), server.getPort());
                DataOutputStream o = new DataOutputStream(socket.getOutputStream());
                String send = SerializeData.toString(sendDataOverNetwork);
                o.writeUTF(send);
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        if (sendDataOverNetwork.getSendTo().equalsIgnoreCase("ALL")) {
            getServersLists().values().forEach(server -> {
                try {
                    socket = new Socket(server.getAddress(), server.getPort());
                    DataOutputStream o = new DataOutputStream(socket.getOutputStream());
                    String send = SerializeData.toString(sendDataOverNetwork);
                    o.writeUTF(send);
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
        }

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




    public void makeConfig() throws IOException {
        if (!getDataFolder().exists()) {
            getLogger().info("Created config folder: " + getDataFolder().mkdir());
        }

        File configFile = new File(getDataFolder(), "network.yml");

        if (!configFile.exists()) {
            FileOutputStream outputStream = new FileOutputStream(configFile); // Throws IOException
            InputStream in = getResourceAsStream("network.yml"); // This file must exist in the jar resources folder
            in.transferTo(outputStream); // Throws IOException
        }
    }


    public Configuration getConfiguration() {
        return configuration;
    }

    public HashMap<String, ServersList> getServersLists() {
        return serversLists;
    }
}
