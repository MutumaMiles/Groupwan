package com.easyshare;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by mutuma on 7/31/2016.
 */
public class FileTransmitter extends Thread
{
    private ObjectOutputStream sendFile;
    private Socket socket;
    private FileInputStream readFile;
    private final int filePORT=64579;
    private String ipAddress;
    private File fileToSend;

    public FileTransmitter(String ipToSend,File _fileToSend)
    {
        this.ipAddress=ipToSend;
        this.fileToSend=_fileToSend;

        try {
            socket=new Socket(ipAddress,filePORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run()
    {
        try {
            sendFile=new ObjectOutputStream(socket.getOutputStream());
            sendFile.writeObject(DatabaseModel.getUsername());
            sendFile.writeObject(fileToSend.getName());
            readFile=new FileInputStream(fileToSend);
            Integer bytesToRead=0;
            byte[] buffer=new byte[100];

            while((bytesToRead=readFile.read(buffer))>0)
            {
                sendFile.writeObject(bytesToRead);
                sendFile.writeObject(Arrays.copyOf(buffer,buffer.length));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
