package com.example.guiversion;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import atlantafx.base.theme.*;


import java.io.IOException;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("scene1.fxml"));
            Scene scene = new Scene(root);
            Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
            Image icon = new Image("C:\\Users\\cusnaruto\\Downloads\\Uni Stuffs\\OOP\\BigProject\\GUIVersion\\GUIVersion\\src\\icon.jpg");
            stage.getIcons().add(icon);
            stage.setTitle("chit fumo");
            stage.setWidth(1280);
            stage.setHeight(720);
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(event -> {
                event.consume();
                logout(stage);
            });
        } catch(Exception e) {
        e.printStackTrace();
        }
    }
    public void logout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Thoát?");
        alert.setHeaderText("Muốn thoát sao homie?");
        alert.setContentText("Muốn save không homie?");

        if(alert.showAndWait().get() == ButtonType.OK) {
            //stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("le logout had arrived");
            stage.close();
        }

    }
}