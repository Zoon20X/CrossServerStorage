package me.zoon20x.crossserverstorage.bungee.socket;

public class ServersList {

    private final String name;
    private final String address;
    private final int port;


    public ServersList(String name, String address, int port) {
        this.name = name;
        this.address = address;
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }
}
