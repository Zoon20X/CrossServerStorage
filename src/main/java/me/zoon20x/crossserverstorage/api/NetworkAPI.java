package me.zoon20x.crossserverstorage.api;

import me.zoon20x.crossserverstorage.networkUtils.SendType;

import java.util.UUID;

public interface NetworkAPI {
/*
   -----------------------------------------------------------------------
   | Hello and welcome to the CSS NetworkAPI, the methods below allow    |
   |  for sending data across the proxy to another server seamlessly     |
   -----------------------------------------------------------------------

   The methods below work as such

   SendType - This is a enum to specify how the data should travel across the network
            - ALL: This will tell the proxy to send the data provided to all servers linked in the network.yml
            - CONNECTED: This will instruct to the proxy that the data will be sent to whatever server the player is currently attempting to connect to
            - SPECIFIC: This will instruct to the proxy that the data must be sent to a specific server, this is achieved by adding
            the server name at the end of the constructor, and it's matching that with a server/port in the network.yml

            Recommended Uses:

            ALL: Recommended to use when all servers require the data, examples are Inventories, admin tool (mutes, bans, kicks), logging
            CONNECTED: Recommended to use with any player specific data such as currency, levels, anything
            SPECIFIC: Recommended to use when you are wanting something more tightly integrated such as minigames connected to a single lobby
            that way you can send the information to the lobby, this is also handy if you have a minigame specific lobby, you can send data to the minigame lobby
            then from the lobby to the servers lobby or more


    Object (data) - this would be a custom object provided by you to store any data you would like, just create a new object and put anything you would like
                  for example all the basics like strings, booleans, ints, doubles, but you can also do stuff like whole inventories, there really is no limit
                  just make sure you cast the object when you are rephrasing it back to get the data back

     UUID (uuid) - this is for when you are wanting to send data to a server the player is connected/connecting on this is designed to be used when a player
                    swaps server more than anything

*/


    void sendDataToNetwork(SendType sendType, Object data);
    void sendDataToNetwork(SendType sendType, Object data, UUID uuid);
    void sendDataToNetwork(SendType sendType, Object data, String serverName);

}
