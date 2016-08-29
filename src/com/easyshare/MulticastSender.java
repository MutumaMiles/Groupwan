package com.easyshare;

import java.io.IOException;
import java.net.*;

/**
 * Created by mutuma on 7/30/2016.
 */
public class MulticastSender extends Thread
{
    private final int PORT=45123;
    private DatagramSocket socket;

    public static void main(String[] args)
    {
        new MulticastSender().start();
    }

    public void run()
    {
        try {
            socket=new DatagramSocket();

            InetAddress group= InetAddress.getByName("227.4.5.6");
            String message=DatabaseModel.getUsername()+" is online";
            System.out.println("Sent"+message);
            byte[] out=message.getBytes();
            DatagramPacket packet=new DatagramPacket(out,out.length,group,PORT);
            socket.send(packet);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
