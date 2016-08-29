package com.easyshare;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by mutuma on 7/30/2016.
 */
public class MessageListener extends Thread
{
    private ServerSocket messageServer;
    private Socket client;
    private ObjectInputStream input;
    private final int messagePORT=63579;
    private OnlineFriends onlinePals;

    public MessageListener(OnlineFriends online)
    {
        this.onlinePals=online;

        try {
            this.messageServer=new ServerSocket(messagePORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run()
    {
        try {
            while((client=messageServer.accept())!=null)
            {
                String message=null;
                input=new ObjectInputStream(client.getInputStream());
                message=(String)input.readObject();
                onlinePals.writeMessage(message);
                onlinePals.notification("New message received ");

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
