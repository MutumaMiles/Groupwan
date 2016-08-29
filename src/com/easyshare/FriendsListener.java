package com.easyshare;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by mutuma on 7/30/2016.
 */
public class FriendsListener extends Thread {
    private OnlineFriends onlinePals;
    private ObjectInputStream input;
    private FileOutputStream writePicture;
    private File file;
    private ServerSocket server;
    private Socket client;

    public FriendsListener(OnlineFriends online) {
        this.onlinePals = online;
        try {
            server = new ServerSocket(62123);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while ((client = server.accept()) != null) {
                input = new ObjectInputStream(client.getInputStream());
                String friendsName = null;
                Object receivedObject = input.readObject();
                byte[] buffer;
                if (receivedObject instanceof String) {

                    friendsName = (String) receivedObject;
                    System.out.println(friendsName);
                }
                receivedObject = input.readObject();
                if (receivedObject instanceof String) {
                    String imageName = (String) receivedObject;

                    if (imageName.endsWith(".jpg")) {
                        System.out.println(imageName);
                        file = new File("c://users//" + System.getProperty("user.name") + "//GroupWan//GroupWan Files//Profile " +
                                "Pictures//" + imageName);
                        writePicture = new FileOutputStream(file);
                        System.out.println("After");
                    }
                }
                Integer bytesRead = 0;
                do {
                    receivedObject = input.readObject();
                    bytesRead = (Integer) receivedObject;
                    receivedObject = input.readObject();
                    buffer = (byte[]) receivedObject;
                    writePicture.write(buffer, 0, bytesRead);

                } while (bytesRead == 100);
                String friendsAddress=client.getInetAddress().getHostAddress();
                if(!(DatabaseModel.checkUser(friendsName, friendsAddress))){
                    System.out.println();
                    DatabaseModel.insertFriendDetails(friendsName, client.getInetAddress().getHostAddress(), file.toString());
                    onlinePals.friend(friendsName, file);
                    onlinePals.notification(friendsName + " is online");
                    String myName = System.getProperty("user.name");
                    String location = DatabaseModel.getImageURL();
                    String address = client.getInetAddress().getHostAddress();
                    new SendMyDetails(myName, location, address).start();
                }
                else {
                    System.out.println("nigger exists");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

