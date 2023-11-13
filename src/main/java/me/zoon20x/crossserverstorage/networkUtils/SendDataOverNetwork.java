package me.zoon20x.crossserverstorage.networkUtils;

import me.zoon20x.crossserverstorage.spigot.CrossServerStorage;
import org.checkerframework.checker.units.qual.N;

import javax.annotation.Nullable;
import java.io.Serializable;

public class SendDataOverNetwork implements Serializable {
    private final static long serialVersionUID = 1;

    private SendType sendType;
    private String object;
    private String extraData;


    public SendDataOverNetwork(SendType sendType, String object){
        this.sendType = sendType;
        this.object = object;
    }
    public SendDataOverNetwork(SendType sendType, String object, String extraData){
        this.sendType = sendType;
        this.object = object;
        this.extraData = extraData;
    }

    public SendType getSendType() {
        return sendType;
    }

    public String getObject() {
        return object;
    }

    @Nullable
    public String getExtraData() {
        return extraData;
    }
}
