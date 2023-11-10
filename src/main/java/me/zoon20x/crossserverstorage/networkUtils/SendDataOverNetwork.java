package me.zoon20x.crossserverstorage.networkUtils;

import java.io.Serializable;

public class SendDataOverNetwork implements Serializable {
    private final static long serialVersionUID = 1;

    private String server;
    private String object;


    public SendDataOverNetwork(String server, String object){
        this.server = server;
        this.object = object;
    }


    public String getServer() {
        return server;
    }

    public String getObject() {
        return object;
    }
}
