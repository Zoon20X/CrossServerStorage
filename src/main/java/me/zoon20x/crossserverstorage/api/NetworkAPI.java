package me.zoon20x.crossserverstorage.api;

import me.zoon20x.crossserverstorage.networkUtils.SendType;

public interface NetworkAPI {

    void sendDataToNetwork(SendType sendType, Object data);
    void sendDataToNetwork(SendType sendType, Object data, String serverName);

}
