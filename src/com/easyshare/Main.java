package com.easyshare;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Created by mutuma on 7/29/2016.
 */
public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {
        if (DatabaseModel.isUser()) {
            showMainWindow(primaryStage);
        } else {
            showAccountWindow(primaryStage);
        }

    }

    private void showAccountWindow(Stage stage) {

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Account.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    private void showMainWindow(Stage primaryStage) {
        primaryStage.setTitle("GroupWan Messenger");
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("Layout.fxml"));
            primaryStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public void show(Stage stage) {
        showMainWindow(stage);
    }

    public void stop() {
        DatabaseModel.closeConnection();
        DatabaseModel.deleteUsers();
        System.exit(0);

    }

    public static void main(String[] args) {

        File fil = new File("c://users//" + System.getProperty("user.name") + "//GroupWan//");
        File file = new File("c://users//" + System.getProperty("user.name") + "//GroupWan//GroupWan Files//");
        File file2 = new File("c://users//" + System.getProperty("user.name") + "//GroupWan//GroupWan Files//Profile " +
                "Pictures");
        File file3 = new File("c://users//" + System.getProperty("user.name") + "//GroupWan//GroupWan Files//Received" +
                " Files//");
        if (!file.exists()) {
            fil.mkdir();
            file.mkdir();
            file2.mkdir();
            file3.mkdir();
        }


        launch(args);

    }
}
