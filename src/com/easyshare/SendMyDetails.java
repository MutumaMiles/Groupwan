package com.easyshare;


import java.io.*;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by mutuma on 7/30/2016.
 */
public class SendMyDetails extends Thread
{
    private ObjectOutputStream output;
    private Socket connection=null;
    private FileInputStream picture;
    private String myName;
    private final int port=62123;
    private File imageLocation;
    private String ipAddress;

    public SendMyDetails(String name, String profilePictureLocation, String hostIp)
    {
        this.myName=name;
        this.imageLocation=new File(profilePictureLocation);
        this.ipAddress=hostIp;

        try {
            connection=new Socket(ipAddress,port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run()
    {
        try {
            output=new ObjectOutputStream(connection.getOutputStream());
            output.writeObject(DatabaseModel.getUsername());
            output.writeObject(imageLocation.getName());

            picture=new FileInputStream(imageLocation);
            byte[] buffer=new byte[100];

            Integer bytesRead=0;
            while((bytesRead=picture.read(buffer))>0)
            {
                output.writeObject(bytesRead);
                output.writeObject(Arrays.copyOf(buffer,buffer.length));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
