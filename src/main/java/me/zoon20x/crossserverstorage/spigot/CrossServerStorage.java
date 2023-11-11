package me.zoon20x.crossserverstorage.spigot;


import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import me.zoon20x.crossserverstorage.api.CrossServerAPI;
import me.zoon20x.crossserverstorage.networkUtils.BasicObject;
import me.zoon20x.crossserverstorage.networkUtils.ServerSocketUtils;
import me.zoon20x.crossserverstorage.spigot.events.NetEvents;
import me.zoon20x.crossserverstorage.networkUtils.SendType;
import me.zoon20x.crossserverstorage.spigot.socket.SocketSend;
import me.zoon20x.crossserverstorage.spigot.socket.SocketUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;


public final class CrossServerStorage extends JavaPlugin {
    private static CrossServerStorage instance;
    private SocketUtils socketUtils;
    private SocketSend socketSend;
    private ServerSocketUtils serverSocketUtils;
    private YamlDocument config;


    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        createConfig("config.yml");
        String proxyAddress = config.getString("Proxy.address");
        int proxyPort = config.getInt("Proxy.port");
        int networkPort = config.getInt("NetworkPort");


        this.serverSocketUtils = new ServerSocketUtils(networkPort);
        this.socketUtils = new SocketUtils();
        this.socketSend = new SocketSend(proxyAddress, proxyPort);

        if(networkPort == 3001){
            socketSend.sendDataToNetwork(SendType.ALL, new BasicObject("Lobby"));
        }

        Bukkit.getPluginManager().registerEvents(new NetEvents(), this);

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

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        serverSocketUtils.close();
        socketUtils.getTask().cancel();
    }


    public YamlDocument getPluginConfig(){
        return config;
    }
    public static CrossServerStorage getInstance(){
        return instance;
    }
    public SocketUtils getSocketUtils() {
        return socketUtils;
    }
    public SocketSend getSocketSend() {
        return socketSend;
    }

    public ServerSocketUtils getServerSocketUtils() {
        return serverSocketUtils;
    }



}
