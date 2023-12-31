package me.zoon20x.crossserverstorage.proxy.bungee.socket;


import me.zoon20x.crossserverstorage.proxy.bungee.CrossServerStorage;
import me.zoon20x.crossserverstorage.networkUtils.SendDataOverNetwork;
import me.zoon20x.crossserverstorage.networkUtils.SerializeData;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.scheduler.ScheduledTask;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class SocketUtils {

    private ServerSocket serverSocket;
    private ScheduledTask task;


    public SocketUtils() {
        startConnectionCheck();
        this.serverSocket = CrossServerStorage.getInstance().getServerSocketUtils().getServerSocket();

    }




    private void startConnectionCheck() {
        task = ProxyServer.getInstance().getScheduler().schedule(CrossServerStorage.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(!serverSocket.isClosed()) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        if (serverSocket.isClosed()) return;
                        //Get input snd output stream
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        DataInputStream a = new DataInputStream(clientSocket.getInputStream());
                        String c = a.readUTF();
                        System.out.println(c);

                        try {
                            SendDataOverNetwork data = (SendDataOverNetwork) SerializeData.setData(c);
                            CrossServerStorage.getInstance().getProxySend().sendDataToServer(data);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }



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
        }, 0, 100, TimeUnit.MILLISECONDS);
    }


    public ScheduledTask getTask() {
        return task;
    }
}
