package com.easyshare;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FileHistory implements Initializable {


    @FXML
    private ListView<Label> listFolders;
    String noFiles = "Folder Empty";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File path = new File("c://users//" + System.getProperty("user.name") + "//GroupWan//GroupWan Files//");
        File[] files = path.listFiles();
        for (File file : files) {
            if (file != null)
                iterateFolders(file.toString());
        }
    }

    public void iterateFolders(String name) {
        try {
            File file = new File(name);
            Label label = new Label(name);
            if (file.isDirectory()) {
                label.setGraphic(new ImageView(new Image(new FileInputStream("src/icons/folders.png"))));
                listFolders.getItems().add(label);
            } else listFolders.getItems().add(label);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void HandleClickedFolder() {
        File item = new File((listFolders.getSelectionModel().getSelectedItem()).getText());
        if (item.isDirectory()) {
            listFolders.getItems().clear();
            File[] names = item.listFiles();
            if (names != null)
                for (File name : names) iterateFolders(name.toString());
        } else {
            new Thread(() -> {
                try {
                    Desktop.getDesktop().open(item);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();
        }
    }
}
