package com.example.guiversion;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Scene2Controller {
    @FXML
    Label age;

    public void displayName(String Theage) {
        age.setText(Theage);
    }
}
