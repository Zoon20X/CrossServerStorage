package me.zoon20x.crossserverstorage.proxy.bungee;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import me.zoon20x.crossserverstorage.networkUtils.ProxySend;
import me.zoon20x.crossserverstorage.proxy.bungee.socket.ServersList;
import me.zoon20x.crossserverstorage.proxy.bungee.socket.SocketUtils;
import me.zoon20x.crossserverstorage.networkUtils.ServerSocketUtils;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public final class CrossServerStorage extends Plugin {
    private static CrossServerStorage instance;
    private SocketUtils socketUtils;
    private ServerSocketUtils serverSocketUtils;
    private YamlDocument config;
    private HashMap<String, ServersList> serversLists = new HashMap<>();
    private ProxySend proxySend;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        createConfig("network.yml");
        loadServers();
        this.serverSocketUtils = new ServerSocketUtils(config.getInt("NetworkPort"));
        this.socketUtils = new SocketUtils();
        this.proxySend = new ProxySend();
        getProxy().getPluginManager().registerListener(this, new BungeeEvents());
    }



    private void createConfig(String fileName){
        try {

            config = YamlDocument.create(new File(getDataFolder(), fileName),
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

    private void loadServers(){
        config.getSection("Servers").getKeys().forEach(serverInfo ->{
            System.out.println(config.getSection("Servers").getKeys());
            System.out.println(serverInfo);
            serversLists.put(serverInfo.toString(), new ServersList(serverInfo.toString(), config.getString("Servers."+serverInfo+".address"), config.getInt("Servers."+serverInfo+".port")));
        });
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
    public YamlDocument getPluginConfig() {
        return config;
    }

    public HashMap<String, ServersList> getServersLists() {
        return serversLists;
    }
    public ProxySend getProxySend(){
        return proxySend;
    }
}
