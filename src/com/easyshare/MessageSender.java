package com.easyshare;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by mutuma on 7/30/2016.
 */
public class MessageSender extends Thread
{
    private Socket socket;
    private ObjectOutputStream output;
    private final int messagePORT=63579;
    private String messageToSend;
    private String friendsIP;
    private String myName=DatabaseModel.getUsername();

    public MessageSender(String message,String _friendsIP)
    {
        this.messageToSend=message;
        this.friendsIP=_friendsIP;

        try {
            socket=new Socket(friendsIP,messagePORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run()
    {
        try {
            output=new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(myName+": "+messageToSend);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
