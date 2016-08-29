package com.easyshare;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;

import javafx.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by mutuma on 7/31/2016.
 */
public class CreateHotspotController
{
    @FXML
    private JFXTextField hotspotName;
    @FXML
    private JFXTextField hotspotPassword;

    public void createHotspot(ActionEvent event)
    {
        if(hotspotName.getText().equals("")||hotspotPassword.getText().equals(""))
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Creating Hotspot");
            alert.setContentText("Please provide both the hotspot name and hostpot password");
            alert.showAndWait();
        }
        else if(hotspotPassword.getText().length()<8)
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Creating Hotspot");
            alert.setContentText("tThe password should be more than 8 letters or characters");
            alert.showAndWait();
        }
        else {
            hotspot(hotspotName.getText(),hotspotPassword.getText());
            ((Node)event.getSource()).getScene().getWindow().hide();
        }
    }
    public void cancelButton(ActionEvent event)
    {
        ((Node)event.getSource()).getScene().getWindow().hide();
    }
    {

    }
    private void hotspot(String name,String password)
    {
        String command="netsh wlan set hostednetwork mode=allow ssid="+name+" key="+password;
        try {
            Process execute=Runtime.getRuntime().exec(command);
            BufferedReader buffer=new BufferedReader(new InputStreamReader(execute.getInputStream()));
            String reader="";
            int count=0;
            while((reader=buffer.readLine())!=null)
            {
                count=count+1;
            }
            if(count==4)
            {
                String command2="netsh wlan start hostednetwork";
                Process startHotspot=Runtime.getRuntime().exec(command2);
                BufferedReader buffer2=new BufferedReader(new InputStreamReader(startHotspot.getInputStream()));
                String reader2=null;
                int count1=0;
                while((reader2=buffer2.readLine())!=null)
                {

                    count1=count1+1;

                }
                if(count1==2)
                {
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success Creating Hotspot");
                    alert.setContentText("Hotspot succesfully created");
                    alert.showAndWait();
                    new MulticastSender().start();
                }
                else
                {
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Creating Hotspot");
                    alert.setContentText("Please check your adapter settings");
                    alert.showAndWait();
                }

            }
            else
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Creating Hotspot");
                alert.setContentText("Please try running this application as an administrator or your computer does not support this option");
                alert.showAndWait();
            }

        } catch (IOException e) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Creating Hotspot");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
}
