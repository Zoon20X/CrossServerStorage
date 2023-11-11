package me.zoon20x.crossserverstorage.spigot.socket.network;

import org.bukkit.Bukkit;

public class NetworkEvent {





    public static void triggerNetworkReceiveEvent(Object data){
        NetworkReceiveEvent networkReceiveEvent = new NetworkReceiveEvent(data);
        Bukkit.getPluginManager().callEvent(networkReceiveEvent);
    }




}
