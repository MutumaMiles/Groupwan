package com.easyshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mutuma on 7/29/2016.
 */
public class DatabaseModel
{
    private static Connection connection=Database.getConnection();
public static void closeConnection(){
    try {
        connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public static boolean isUser()
    {
        String sql="SELECT * FROM account";
        PreparedStatement getUser=null;
        ResultSet results=null;

        try
        {
            getUser=connection.prepareStatement(sql);
            results=getUser.executeQuery();

            if(results.next())
            {
                return true;
            }
            else
            {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean userDetails(String username,String imageURL)
    {
        PreparedStatement insert=null;
        int result=0;

        try {
            insert=connection.prepareStatement("INSERT INTO account(username,image)VALUES(?,?)");
            insert.setString(1,username);
            insert.setString(2,imageURL);

            result=insert.executeUpdate();
            if(result==1)
            {
                return true;
            }
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static String getImageURL()
    {
        PreparedStatement getImage=null;
        ResultSet results=null;

        try {
            getImage=connection.prepareStatement("SELECT image FROM account");
            results=getImage.executeQuery();
            while(results.next())
            {
                return results.getString("image");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    public static String getUsername()
    {
        PreparedStatement getusername=null;
        ResultSet results=null;

        try {
            getusername=connection.prepareStatement("SELECT username FROM account");
            results=getusername.executeQuery();
            while(results.next())
            {
                return results.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    public static void insertFriendDetails(String name,String ipAddress,String image)
    {
        PreparedStatement insertDetails=null;

        try {
            insertDetails=connection.prepareStatement("INSERT INTO friends(friendName,ipAddress,image)VALUES(?,?,?)");
            insertDetails.setString(1,name);
            insertDetails.setString(2,ipAddress);
            insertDetails.setString(3,image);

            insertDetails.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String getIpAddress(String name)
    {
        PreparedStatement getIpAddress=null;
        ResultSet results=null;

        try {
            getIpAddress=connection.prepareStatement("SELECT ipAddress FROM friends WHERE friendName=?");
            getIpAddress.setString(1,name);

            results=getIpAddress.executeQuery();
            while(results.next())
            {
                return results.getString("ipAddress");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
    public static boolean checkUser(String name,String ip){
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try{
            preparedStatement=connection.prepareStatement("SELECT friendName,ipAddress FROM friends WHERE friendName=? AND ipAddress=?");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,ip);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){

                return true;
            }
            else {
                return false;
            }
        }catch (Exception e){
            System.out.println("Error");
            e.printStackTrace();
            return false;
        }

    }
    public static void deleteUsers(){
        PreparedStatement deleteUsers=null;
        try {
            deleteUsers= Database.getConnection().prepareStatement("DROP TABLE friends");
            deleteUsers.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            e.getMessage();
        }
    }
}
