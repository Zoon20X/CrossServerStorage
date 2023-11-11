package me.zoon20x.crossserverstorage.spigot.socket.network;

import com.velocitypowered.api.proxy.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class NetworkLeaveEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final UUID player;

    public NetworkLeaveEvent(UUID player) {
        super(true);
        this.player = player;

    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }


    public UUID getPlayer() {
        return player;
    }
}
