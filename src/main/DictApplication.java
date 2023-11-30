import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.*;
import atlantafx.base.theme.*;

import java.io.IOException;
import base.*;
public class DictApplication extends Application {
    protected static boolean isDarkMode = true;
    public static DBDictionary dbDictionary;
    public static boolean isDarkMode() {
        return isDarkMode;
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        dbDictionary = new DBDictionary();
        try {
            dbDictionary.initialize();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
            mainMenuController mainMenuController = loader.getController();
            Image icon = new Image("icon.jpg");
            stage.getIcons().add(icon);
            stage.setTitle("MintTwister");
            stage.setWidth(1280);
            stage.setHeight(720);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(event -> {
                event.consume();
                logout(stage);
            });
        } catch(Exception e) {
            System.err.println("loi ne");
        e.printStackTrace();
        }
    }
    public void logout(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exiting...");
        alert.setHeaderText("Bạn đang muốn thoát?");
        alert.setContentText("Nếu có, hãy bấm OK");

        if(alert.showAndWait().get() == ButtonType.OK) {
            //stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("le logout had arrived");
            stage.close();
        }

    }
}
