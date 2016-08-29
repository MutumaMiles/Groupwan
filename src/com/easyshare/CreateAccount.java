package com.easyshare;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

/**
 * Created by mutuma on 7/29/2016.
 */
public class CreateAccount
{
    @FXML
    private JFXTextField usernameField;
    @FXML
    private ImageView profileImage;
    private File getFile=null;
    private Stage stage=new Stage();
   private static boolean userCreatedAccount=false;

    public void createAccount()
    {
        FileChooser chooseFile=new FileChooser();
        chooseFile.setTitle("Choose a profile picture");
        chooseFile.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG","*.jpg"),new FileChooser.ExtensionFilter("PNG","*.png"));
        getFile=chooseFile.showOpenDialog(null);

        if(getFile!=null|| usernameField.getText().equals(""))
        {
            Image image= null;
            try {
                image = new Image(getFile.toURI().toURL().toExternalForm(),200,400,true,true);
                profileImage.setImage(image);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
        else
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Please choose Image or fill in the username field");
            alert.setContentText("Please select an Image");
            alert.show();
            createAccount();
        }
    }
    public void createAccountButton(ActionEvent event)
    {
        if(getFile!=null&&usernameField.getText().length()>1) {
            if (DatabaseModel.userDetails(usernameField.getText(), getFile.toString())) {
                userCreatedAccount = true;
                if (userCreatedAccount) {
                    ((Node)event.getSource()).getScene().getWindow().hide();


                    new Main().show(stage);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Creating An account");
                alert.setContentText("Database Error");
                alert.show();


            }
        }else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Creating An account");
            alert.setContentText("Please fill all the required details");
            alert.show();


        }
    }


}
