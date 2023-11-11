package me.zoon20x.crossserverstorage.velocity.socket;


import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.scheduler.ScheduledTask;
import com.velocitypowered.api.scheduler.Scheduler;
import me.zoon20x.crossserverstorage.networkUtils.SendDataOverNetwork;
import me.zoon20x.crossserverstorage.networkUtils.SerializeData;
import me.zoon20x.crossserverstorage.velocity.CrossServerStorage;


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
        task = CrossServerStorage.getInstance().getServer().getScheduler().buildTask(CrossServerStorage.getInstance(),() -> {
            if(!serverSocket.isClosed()) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("connected");
                    if (serverSocket.isClosed()) return;
                    //Get input snd output stream
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    System.out.println("PROCESS");
                    DataInputStream a = new DataInputStream(clientSocket.getInputStream());
                    String c = a.readUTF();
                    System.out.println(c);

                    try {
                        SendDataOverNetwork data = (SendDataOverNetwork) SerializeData.setData(c);
                        CrossServerStorage.getInstance().sendDataToServer(data);
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
        }).repeat(100L,TimeUnit.MILLISECONDS).schedule();
    }


    public ScheduledTask getTask() {
        return task;
    }
}
