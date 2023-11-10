package me.zoon20x.crossserverstorage.networkUtils;

import me.zoon20x.crossserverstorage.spigot.CrossServerStorage;
import org.checkerframework.checker.units.qual.N;

import javax.annotation.Nullable;
import java.io.Serializable;

public class SendDataOverNetwork implements Serializable {
    private final static long serialVersionUID = 1;

    private String sendTo;
    private String object;
    private String serverName;


    public SendDataOverNetwork(String sendTo, String object){
        this.sendTo = sendTo;
        this.object = object;
    }
    public SendDataOverNetwork(String sendTo, String object, String serverName){
        this.sendTo = sendTo;
        this.object = object;
        this.serverName = serverName;
    }

    public String getSendTo() {
        return sendTo;
    }

    public String getObject() {
        return object;
    }

    @Nullable
    public String getServerName() {
        return serverName;
    }
}
