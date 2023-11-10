package me.zoon20x.crossserverstorage.networkUtils;


import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketUtils {

    private ServerSocket serverSocket;


    public ServerSocketUtils(int port){
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ServerSocket getServerSocket(){
        return serverSocket;
    }

    public void close(){
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





}
