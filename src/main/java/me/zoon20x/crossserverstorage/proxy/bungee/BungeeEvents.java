package me.zoon20x.crossserverstorage.proxy.bungee;

import me.zoon20x.crossserverstorage.networkUtils.ProxyLeaveData;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;
import java.util.UUID;

public class BungeeEvents implements Listener {
    private HashMap<UUID, String> lastKnownServer = new HashMap<>();


    @EventHandler
    public void onSwitch(ServerSwitchEvent event){

    }

    @EventHandler
    public void onLeave(ServerDisconnectEvent event){
        ProxiedPlayer player =event.getPlayer();
        ServerInfo info = event.getTarget();
        lastKnownServer.put(player.getUniqueId(), info.getName());
    }

    @EventHandler
    public void onQuit(PlayerDisconnectEvent event){
        ProxiedPlayer player = event.getPlayer();
        if(lastKnownServer.isEmpty() || !lastKnownServer.containsKey(player.getUniqueId())){
            return;
        }
        CrossServerStorage.getInstance().getProxySend().sendProxyLeaveData(new ProxyLeaveData(lastKnownServer.get(player.getUniqueId()), player.getUniqueId()));
    }


}
