package com.easyshare;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by mutuma on 7/30/2016.
 */
public class MulticastReceiver extends Thread
{
    private OnlineFriends onlinePals;
    public MulticastReceiver(OnlineFriends online)
    {
        this.onlinePals=online;
    }

    public void run()
    {
        try {
            MulticastSocket socket=new MulticastSocket(45123);
            socket.joinGroup(InetAddress.getByName("227.4.5.6"));
            byte[] in=new byte[256];
            System.out.println("Waiting to receive packets");
            while(true) {
                DatagramPacket packet = new DatagramPacket(in, in.length);
                socket.receive(packet);
                String message=new String(in,0,packet.getLength());

                onlinePals.notification(message);
                String address = packet.getAddress().getHostAddress();
                System.out.println(address);
                SendMyDetails myDetails = new SendMyDetails(DatabaseModel.getUsername(), DatabaseModel.getImageURL(), address);
                myDetails.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
