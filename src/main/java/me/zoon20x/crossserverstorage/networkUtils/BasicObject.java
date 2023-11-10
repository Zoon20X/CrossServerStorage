package me.zoon20x.crossserverstorage.networkUtils;

import java.io.Serializable;

public class BasicObject implements Serializable {
    private String from;

    public BasicObject(String from){
        this.from = from;
    }


    public String runCode(){
        return "SERVER/BAN/" + from + "/NETWORK";
    }


}
