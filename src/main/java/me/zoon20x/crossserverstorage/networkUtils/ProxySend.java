package me.zoon20x.crossserverstorage.networkUtils;


import me.zoon20x.crossserverstorage.proxy.bungee.CrossServerStorage;
import me.zoon20x.crossserverstorage.proxy.bungee.socket.ServersList;

import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;

public class ProxySend {


    private Socket socket;

    public ProxySend(){

    }



    public void sendProxyLeaveData(ProxyLeaveData proxyLeaveData) {
        try {
            ServersList server = CrossServerStorage.getInstance().getServersLists().get(proxyLeaveData.getServer());
            socket = new Socket(server.getAddress(), server.getPort());
            DataOutputStream o = new DataOutputStream(socket.getOutputStream());
            String send = SerializeData.toString(proxyLeaveData);
            o.writeUTF(send);
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendDataToServer(SendDataOverNetwork sendDataOverNetwork) {
        if (sendDataOverNetwork.getSendType() == SendType.SPECIFIC) {
            try {
                ServersList server = CrossServerStorage.getInstance().getServersLists().get(sendDataOverNetwork.getServerName());
                socket = new Socket(server.getAddress(), server.getPort());
                DataOutputStream o = new DataOutputStream(socket.getOutputStream());
                String send = SerializeData.toString(sendDataOverNetwork);
                o.writeUTF(send);
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        if (sendDataOverNetwork.getSendType() == SendType.ALL) {
            CrossServerStorage.getInstance().getServersLists().values().forEach(server -> {
                try {
                    socket = new Socket(server.getAddress(), server.getPort());
                    DataOutputStream o = new DataOutputStream(socket.getOutputStream());
                    String send = SerializeData.toString(sendDataOverNetwork);
                    o.writeUTF(send);
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
        }

    }

}
