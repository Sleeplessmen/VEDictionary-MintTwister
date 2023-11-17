import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.*;
import atlantafx.base.theme.*;

public class DictApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
            Scene scene = new Scene(root);
            Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
            Image icon = new Image("icon.jpg");
            stage.getIcons().add(icon);
            stage.setTitle("chit fumo");
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
