package me.zoon20x.crossserverstorage.spigot.events;

import me.zoon20x.crossserverstorage.networkUtils.BasicObject;
import me.zoon20x.crossserverstorage.spigot.socket.network.NetworkReceiveEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NetEvents implements Listener {



    @EventHandler
    public void onNetworkReceive(NetworkReceiveEvent event){
        BasicObject object = (BasicObject) event.getData();
        System.out.println(object.runCode());
    }


}
