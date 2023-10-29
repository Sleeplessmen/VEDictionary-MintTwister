package com.example.guiversion;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Scene1Controller implements Initializable {
    @FXML
    TextField ageTextField;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Button logoutButton;
    @FXML
    private AnchorPane scenePane;
    int level;
    @FXML
    private Label myTextLabel;
    @FXML
    ImageView myImageView;
    @FXML
    private CheckBox checkBox;
    @FXML
    private Button myButton;
    public void selectItem() {
        TreeItem<String> item = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
        if (item != null) {
            System.out.println(item.getValue());
        }
    }
    @FXML
    private TreeView treeView;
    @FXML
    private Button webViewbutton;

    public Scene1Controller() {
    }

    int age;
    Image myImage1 = new Image(getClass().getResourceAsStream("image1.jpg"));
    Image myImage2 = new Image(getClass().getResourceAsStream("image2.jpg"));
    public void changeto3(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene3.fxml"));
        root = loader.load();
        Scene3Controller scene3Controller = loader.getController();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void displayImage() {
        myImageView.setImage(myImage2);
    }
    public void change(ActionEvent event) {
        if (checkBox.isSelected()) {
            myImageView.setImage(myImage2);
        }
        else {
            myImageView.setImage(myImage1);
        }
    }
    public void login(ActionEvent event) throws IOException {
        //root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
        try {
            age = Integer.parseInt(ageTextField.getText());
            if (!(age >= 0 && age <= 120)) {
                myTextLabel.setText("Tuổi này hơi sai");
            }
            else {
                String age = ageTextField.getText();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("scene2.fxml"));
                root = loader.load();
                Scene2Controller scene2Controller = loader.getController();
                scene2Controller.displayName(age);
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (NumberFormatException e){
            System.out.println("Tuoi khong hop le (no la chu cai)");
            myTextLabel.setText("Hãy chỉ nhập số");
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }

    public void logout(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Thoát?");
        alert.setHeaderText("Muốn thoát sao homie?");
        alert.setContentText("Muốn save không homie?");

        if(alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("le logout had arrived");
            stage.close();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TreeItem<String> rootItem = new TreeItem<>("Map");

        TreeItem<String> branchItem1 = new TreeItem<>("NM1");
        TreeItem<String> branchItem2 = new TreeItem<>("NM2");
        TreeItem<String> branchItem3 = new TreeItem<>("NM3");
        TreeItem<String> branchItem4 = new TreeItem<>("NM4");

        TreeItem<String> leafItem1 = new TreeItem<>("Consist");
        TreeItem<String> leafItem2 = new TreeItem<>("Stream");
        TreeItem<String> leafItem3 = new TreeItem<>("Alt");
        TreeItem<String> leafItem4 = new TreeItem<>("Tech");

        rootItem.getChildren().addAll(branchItem1,branchItem2,branchItem3,branchItem4);
        branchItem1.getChildren().addAll(leafItem1);
        branchItem2.getChildren().addAll(leafItem2);
        branchItem3.getChildren().addAll(leafItem3);
        branchItem4.getChildren().addAll(leafItem4);


        treeView.setRoot(rootItem);
    }
}
