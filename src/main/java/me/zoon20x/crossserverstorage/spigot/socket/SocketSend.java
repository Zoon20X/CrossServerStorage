package me.zoon20x.crossserverstorage.spigot.socket;

import me.zoon20x.crossserverstorage.api.NetworkAPI;
import me.zoon20x.crossserverstorage.networkUtils.SendDataOverNetwork;
import me.zoon20x.crossserverstorage.networkUtils.SendType;
import me.zoon20x.crossserverstorage.networkUtils.SerializeData;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public class SocketSend implements NetworkAPI {


    private Socket socket;
    private String proxyAddress;
    private int proxyPort;

    public SocketSend(String proxyAddress, int proxyPort){
        this.proxyAddress = proxyAddress;
        this.proxyPort = proxyPort;
    }



    @Override
    public void sendDataToNetwork(SendType sendType, Object data){
        try {
            socket = new Socket(proxyAddress, proxyPort);
            DataOutputStream o = new DataOutputStream(socket.getOutputStream());
            String b = SerializeData.toString((Serializable) data);
            SendDataOverNetwork over = new SendDataOverNetwork(sendType, b);
            String send = SerializeData.toString(over);
            o.writeUTF(send);
            socket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void sendDataToNetwork(SendType sendType, Object data, String server){
        try {
            socket = new Socket(proxyAddress, proxyPort);
            DataOutputStream o = new DataOutputStream(socket.getOutputStream());
            String b = SerializeData.toString((Serializable) data);
            SendDataOverNetwork over = new SendDataOverNetwork(sendType, b, server);
            String send = SerializeData.toString(over);
            o.writeUTF(send);
            socket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
