package me.zoon20x.crossserverstorage.spigot.events;

import me.zoon20x.crossserverstorage.api.CrossServerAPI;
import me.zoon20x.crossserverstorage.networkUtils.SendType;
import me.zoon20x.crossserverstorage.spigot.socket.network.NetworkLeaveEvent;
import me.zoon20x.crossserverstorage.spigot.socket.network.NetworkReceiveEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class NetEvents implements Listener {

    @EventHandler
    public void onNetworkReceive(NetworkReceiveEvent event){

    }

    @EventHandler
    public void onNetworkLeave(NetworkLeaveEvent event){

    }


}
