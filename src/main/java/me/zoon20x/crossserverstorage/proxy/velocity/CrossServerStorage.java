package me.zoon20x.crossserverstorage.proxy.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import me.zoon20x.crossserverstorage.api.CrossServerAPI;
import me.zoon20x.crossserverstorage.networkUtils.*;
import me.zoon20x.crossserverstorage.proxy.velocity.socket.ServersList;
import me.zoon20x.crossserverstorage.proxy.velocity.socket.SocketUtils;
import org.slf4j.Logger;

import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.UUID;

@Plugin(
        id = "crossserverstorage",
        name = "CrossServerStorage",
        version = "1.0-SNAPSHOT"
)
public class CrossServerStorage {

    private final ProxyServer server;
    private SocketUtils socketUtils;
    private ServerSocketUtils serverSocketUtils;
    private Path dataDirectory;
    private static CrossServerStorage instance;
    private HashMap<String, ServersList> serversLists = new HashMap<>();

    private YamlDocument config;

    private ProxySend proxySend;

    @Inject
    private Logger logger;


    @Inject
    public CrossServerStorage(ProxyServer server, Logger logger, @DataDirectory Path dataDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = dataDirectory;
        createConfig("network.yml");
    }
    private void createConfig(String fileName){
        try {
            config = YamlDocument.create(new File(dataDirectory.toFile(), fileName),
                    getClass().getResourceAsStream("/"+fileName),
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
        this.proxySend = new ProxySend();

    }
    private void loadServers(){
        config.getSection("Servers").getKeys().forEach(serverInfo ->{
            System.out.println(serverInfo.toString());
            serversLists.put(serverInfo.toString(), new ServersList(serverInfo.toString(), config.getString("Servers."+serverInfo+".address"), config.getInt("Servers."+serverInfo+".port")));
        });
    }


    private HashMap<UUID, String> lastKnownServer = new HashMap<>();


    @Subscribe
    public void onSwitch(ServerConnectedEvent event){
        Player player = event.getPlayer();
        RegisteredServer ser = event.getServer();
        lastKnownServer.put(player.getUniqueId(), ser.getServerInfo().getName());
    }

    @Subscribe
    public void onQuit(DisconnectEvent event){
        Player player = event.getPlayer();
        if(lastKnownServer.isEmpty() || !lastKnownServer.containsKey(player.getUniqueId())){
            return;
        }
        CrossServerStorage.getInstance().getProxySend().sendProxyLeaveData(new ProxyLeaveData(lastKnownServer.get(player.getUniqueId()), player.getUniqueId()));
    }


    @Subscribe
    public void onProxyClose(ProxyShutdownEvent event){
        serverSocketUtils.close();
        socketUtils.getTask().cancel();
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
    public ProxySend getProxySend(){
        return proxySend;
    }
}