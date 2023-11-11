package me.zoon20x.crossserverstorage.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import me.zoon20x.crossserverstorage.networkUtils.SendDataOverNetwork;
import me.zoon20x.crossserverstorage.networkUtils.SerializeData;
import me.zoon20x.crossserverstorage.networkUtils.ServerSocketUtils;
import me.zoon20x.crossserverstorage.velocity.socket.ServersList;
import me.zoon20x.crossserverstorage.velocity.socket.SocketUtils;
import org.slf4j.Logger;

import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.util.HashMap;

@Plugin(
        id = "crossserverstorage",
        name = "CrossServerStorage",
        version = "1.0-SNAPSHOT"
)
public class CrossServerStorage {

    private final ProxyServer server;
    private SocketUtils socketUtils;
    private ServerSocketUtils serverSocketUtils;
    private Socket socket;
    private static CrossServerStorage instance;
    private HashMap<String, ServersList> serversLists = new HashMap<>();

    private YamlDocument config;

    @Inject
    private Logger logger;


    @Inject
    public CrossServerStorage(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;

        try {
            config = YamlDocument.create(new File(dataDirectory.toFile(), "network.yml"),
                    getClass().getResourceAsStream("/network.yml"),
                    GeneralSettings.DEFAULT,
                    LoaderSettings.builder().setAutoUpdate(true).build(),
                    DumperSettings.DEFAULT,
                    UpdaterSettings.builder().setVersioning(new BasicVersioning("file-version")).setOptionSorting(UpdaterSettings.OptionSorting.SORT_BY_DEFAULTS).build());
            config.update();
            config.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        instance = this;
        loadServers();
        this.serverSocketUtils = new ServerSocketUtils(config.getInt("NetworkPort"));
        this.socketUtils = new SocketUtils();

    }
    private void loadServers(){
        config.getSection("Servers").getKeys().forEach(serverInfo ->{
            System.out.println(serverInfo.toString());
            serversLists.put(serverInfo.toString(), new ServersList(serverInfo.toString(), config.getString("Servers."+serverInfo+".address"), config.getInt("Servers."+serverInfo+".port")));
        });
    }

    @Subscribe
    public void onProxyClose(ProxyShutdownEvent event){
        serverSocketUtils.close();
        socketUtils.getTask().cancel();
    }


    public void sendDataToServer(SendDataOverNetwork sendDataOverNetwork) {
        if (sendDataOverNetwork.getSendTo().equalsIgnoreCase("SERVER")) {
            try {
                ServersList server = getInstance().getServersLists().get(sendDataOverNetwork.getServerName());
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
            getServersLists().values().forEach(servers -> {
                try {
                    socket = new Socket(servers.getAddress(), servers.getPort());
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

    public static CrossServerStorage getInstance() {
        return instance;
    }

    public ProxyServer getServer() {
        return server;
    }

    public SocketUtils getSocketUtils() {
        return socketUtils;
    }

    public ServerSocketUtils getServerSocketUtils() {
        return serverSocketUtils;
    }
    public HashMap<String, ServersList> getServersLists() {
        return serversLists;
    }
    public YamlDocument getConfig(){
        return config;
    }
}