package com.easyshare;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by mutuma on 7/31/2016.
 */
public class FileListener extends Thread {
    private ServerSocket fileServer;
    private ObjectInputStream incomimgFile;
    private final int filePORT = 61234;
    private FileOutputStream writeToFile;
    private Socket connection;
    private OnlineFriends onlinePals;

    public FileListener(OnlineFriends online) {
        this.onlinePals = online;
        try {
            fileServer = new ServerSocket(filePORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while ((connection = fileServer.accept()) != null) {
                incomimgFile = new ObjectInputStream(connection.getInputStream());
                Object recievedFile = incomimgFile.readObject();
                String name = null;
                File fileName = null;
                byte[] buffer;
                if (recievedFile instanceof String) {
                    name = (String) recievedFile;
                }
                recievedFile = incomimgFile.readObject();

                if (recievedFile instanceof String) {
                    fileName = new File((String) recievedFile);
                    writeToFile = new FileOutputStream("c://users//" + System.getProperty("user.name") + "//GroupWan//GroupWan Files//Received" +
                            " Files//"+ fileName);
                    onlinePals.writeMessage("Receiving " + fileName + " from" + name);
                }
                Integer bytesToRead = 0;
                do {
                    recievedFile = incomimgFile.readObject();
                    bytesToRead = (Integer) recievedFile;

                    recievedFile = incomimgFile.readObject();
                    buffer = (byte[]) recievedFile;

                    writeToFile.write(buffer, 0, bytesToRead);

                } while (bytesToRead == 100);
                onlinePals.notification("Received " + fileName + " from " + name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
