package me.zoon20x.crossserverstorage.spigot.socket;


import me.zoon20x.crossserverstorage.networkUtils.BasicObject;
import me.zoon20x.crossserverstorage.networkUtils.SendDataOverNetwork;
import me.zoon20x.crossserverstorage.networkUtils.SerializeData;
import me.zoon20x.crossserverstorage.spigot.CrossServerStorage;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketUtils {
    private ServerSocket serverSocket;
    private Socket socket;
    private String proxyAddress;
    private int proxyPort;
    private BukkitTask task;
    public SocketUtils(String proxyAddress, int proxyPort) {
        this.proxyAddress = proxyAddress;
        this.proxyPort = proxyPort;
        startConnectionCheck();
        this.serverSocket = CrossServerStorage.getInstance().getServerSocketUtils().getServerSocket();
    }



    private void startConnectionCheck(){
        task = new BukkitRunnable() {
            @Override
            public void run() {
                if(!serverSocket.isClosed()) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        System.out.println("connected");
                        if (serverSocket.isClosed()) return;

                        //Get input snd output stream
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        //System.out.println("PROCESS");
                        DataInputStream a = new DataInputStream(clientSocket.getInputStream());
                        String c = a.readUTF();
                        //System.out.println(c);
                        try {
                            SendDataOverNetwork data = (SendDataOverNetwork) SerializeData.setData(c);
                            //System.out.println(data.getSendTo());
                            Object object = SerializeData.setData(data.getObject());
                            NetworkEvent.triggerNetworkReceiveEvent(object);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }


                        //Get input
                        out.close();
                        in.close();
                    } catch (IOException e) {
                        if (serverSocket.isClosed()) {
                            return;
                        }
                        throw new RuntimeException(e);
                    }
                }
            }
        }.runTaskTimerAsynchronously(CrossServerStorage.getInstance(), 0, 5);
    }


    public void sendDataToNetwork(SendTo sendTo, Object data){
        try {
            socket = new Socket(proxyAddress, proxyPort);
            DataOutputStream o = new DataOutputStream(socket.getOutputStream());
            String b = SerializeData.toString((Serializable) data);
            SendDataOverNetwork over = new SendDataOverNetwork(sendTo.toString(), b);
            String send = SerializeData.toString(over);
            o.writeUTF(send);
            socket.close();
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendDataToNetwork(SendTo sendTo, Object data, String server){
        try {
            socket = new Socket(proxyAddress, proxyPort);
            DataOutputStream o = new DataOutputStream(socket.getOutputStream());
            String b = SerializeData.toString((Serializable) data);
            SendDataOverNetwork over = new SendDataOverNetwork(sendTo.toString(), b, server);
            String send = SerializeData.toString(over);
            o.writeUTF(send);
            socket.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public BukkitTask getTask() {
        return task;
    }
}
