package com.easyshare;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mutuma on 7/29/2016.
 */
public class Controller implements Initializable, OnlineFriends {

    Image image = null;
    @FXML
    private JFXButton sendMessageButton, fileHistory;
    @FXML
    private TextArea conversionsArea;
    @FXML
    private ImageView profileImage;
    @FXML
    private Label usernameLabel,activeFriend;
    @FXML
    private JFXListView<ListItems> listView;
    @FXML
    private TextArea messageArea;
    private final ObservableList<ListItems> listData = FXCollections.observableArrayList();
    private File imageLocation = new File(DatabaseModel.getImageURL());
    private String friendsIpAddress;
    private String friendsName = "";


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            image = new Image(imageLocation.toURI().toURL().toExternalForm(), 200, 400, true, true);
            profileImage.setImage(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        profileImage.setImage(image);
        usernameLabel.setText("(You) " + DatabaseModel.getUsername());
        new FriendsListener(this).start();

        new MulticastReceiver(this).start();
        new MulticastSender().start();
        new MessageListener(this).start();
        new FileListener(this).start();


    }
    //Show file history
    public void showFileHistory(){
        try{
            Stage primaryStage=new Stage();
            Parent root=FXMLLoader.load(getClass().getResource("FileHistory.fxml"));
            Scene scene=new Scene(root);
            primaryStage.setTitle("CLICK ONCE TO OPEN A FOLDER/FILE");
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch (Exception e){
            System.out.println("Izeya msee this could not happen. Try another method");
            e.printStackTrace();
        }



    }

    public void listView() {
        listView.setExpanded(true);
    }

    @Override
    public void friend(String name, File profilePicture) {
        listData.add(new ListItems(name, profilePicture));

        listView.setCellFactory(new Callback<ListView<ListItems>, ListCell<ListItems>>() {
            @Override
            public ListCell<ListItems> call(ListView<ListItems> param) {
                ListCell<ListItems> cell = new ListCell<ListItems>() {
                    protected void updateItem(ListItems listObject, boolean bool) {
                        super.updateItem(listObject, bool);
                        if (listObject != null) {
                            Image img = null;
                            try {
                                img = new Image(listObject.getFriendsProfilePic().toURI().toURL().toExternalForm(), 50, 50, true, true);


                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            ImageView image = new ImageView(img);
                            setGraphic(image);
                            setText(listObject.getFriendName());
                        }
                    }

                };
                return cell;
            }
        });
        listView.setItems(listData);

    }

    @Override
    public void writeMessage(String message) {
        conversionsArea.appendText("\n" + message);


    }

    public void selectFriend() {
        friendsName = listView.getItems().get(listView.getSelectionModel().getSelectedIndex()).getFriendName();
        friendsIpAddress = DatabaseModel.getIpAddress(friendsName);
        activeFriend.setText(friendsName);


    }

    public void sendMessage() {
        if (friendsName.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Sending Message");
            alert.setContentText("Please choose a recepient");
            alert.showAndWait();
        } else if (messageArea.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Sending Message");
            alert.setContentText("Please type a message first");
            alert.showAndWait();
        } else {
            String message = messageArea.getText();
            new MessageSender(message, friendsIpAddress).start();
            conversionsArea.appendText("\n"+DatabaseModel.getUsername()+": "+message+"\n");
            messageArea.clear();

        }
    }

    public void sendFile() {
        if (friendsName.equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Sending File");
            alert.setContentText("Please choose a recepient");
            alert.showAndWait();
        }
        else {
            FileChooser chooseFile = new FileChooser();
            chooseFile.setTitle("GroupwanMessage: Choose File to Send to " + friendsName);
            File choosenFile = chooseFile.showOpenDialog(null);
            new FileTransmitter(friendsIpAddress, choosenFile).start();
        }


    }

    public void showHotspotWindow() {
        Stage hotspotStage = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("CreateHotspot.fxml"));
            Scene scene = new Scene(root);
            hotspotStage.setScene(scene);
            hotspotStage.initModality(Modality.APPLICATION_MODAL);
            hotspotStage.setTitle("GroupwanMessanger: Create Hotspot");
            hotspotStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notification(String trayMessage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                NotificationType type = NotificationType.INFORMATION;
                TrayNotification tray = new TrayNotification();
                tray.setTitle("Groupwan Messenger");
                tray.setMessage(trayMessage);
                tray.setNotificationType(type);
                tray.setAnimationType(AnimationType.POPUP);
                tray.showAndDismiss(Duration.seconds(5));

            }
        });


    }
}
