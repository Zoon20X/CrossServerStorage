package me.zoon20x.crossserverstorage.api;

import me.zoon20x.crossserverstorage.networkUtils.SendType;

public interface NetworkAPI {
/*
   -----------------------------------------------------------------------
   | Hello and welcome to the CSS NetworkAPI, the methods below allow    |
   |  for sending data across the proxy to another server seamlessly     |
   -----------------------------------------------------------------------

   The methods below work as such

   SendType - This is a enum to specify how the data should travel across the network
            - ALL: This will tell the proxy to send the data provided to all servers linked in the network.yml
            - PLAYER: This will instruct to the proxy that the data will be sent to whatever server the player is currently attempting to connect to
            - SERVER (less likely used): This will instruct to the proxy that the data must be sent to a specific server, this is achieved by adding
            the server name at the end of the constructor, and it's matching that with a server/port in the network.yml

            Recommended Uses:

            ALL: Recommended to use when all servers require the data, examples are Inventories, admin tool (mutes, bans, kicks), logging
            PLAYER: Recommended to use with any player specific data such as currency, levels,

*/





    void sendDataToNetwork(SendType sendType, Object data);
    void sendDataToNetwork(SendType sendType, Object data, String serverName);

}
