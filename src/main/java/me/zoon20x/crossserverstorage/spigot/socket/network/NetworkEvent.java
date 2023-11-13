package me.zoon20x.crossserverstorage.spigot.socket.network;

import me.zoon20x.crossserverstorage.api.CrossServerAPI;
import me.zoon20x.crossserverstorage.networkUtils.ProxyLeaveData;
import org.bukkit.Bukkit;

public class NetworkEvent {





    public static void triggerNetworkReceiveEvent(Object data){
        NetworkReceiveEvent networkReceiveEvent = new NetworkReceiveEvent(data);
        Bukkit.getPluginManager().callEvent(networkReceiveEvent);
    }
    public static void triggerNetworkLeaveEvent(ProxyLeaveData data){
        NetworkLeaveEvent networkLeaveEvent = new NetworkLeaveEvent(data.getPlayer());
        Bukkit.getPluginManager().callEvent(networkLeaveEvent);
    }



}
