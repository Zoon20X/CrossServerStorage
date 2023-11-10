package me.zoon20x.crossserverstorage.spigot.socket;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class NetworkReceiveEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Object data;

    public NetworkReceiveEvent(Object data) {
        super(true);
        this.data = data;

    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Object getData() {
        return data;
    }
}
