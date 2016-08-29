package com.easyshare;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by mutuma on 7/29/2016.
 */
public class Database
{
    private static Connection connection=null;
    private static PreparedStatement createAccount=null;
    private static PreparedStatement insertFriend=null;
    public static Connection getConnection()
    {
        try {
            Class.forName("org.sqlite.JDBC");
            connection= DriverManager.getConnection("jdbc:sqlite:GroupWan.db");
            createAccount=connection.prepareStatement("CREATE TABLE IF NOT EXISTS account(username STRING,image STRING)");
            insertFriend=connection.prepareStatement("CREATE TABLE IF NOT EXISTS friends(friendName STRING,ipAddress STRING,image STRING)");

            createAccount.executeUpdate();
            insertFriend.executeUpdate();
            return connection;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return connection;
        }

    }
}
