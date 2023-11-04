
import atlantafx.base.theme.Dracula;
import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.PrimerDark;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class settingController {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ToggleGroup Themes;

    @FXML
    private Button backButton;

    @FXML
    private RadioButton themeDracula;

    @FXML
    private RadioButton themeNordDark;

    @FXML
    private RadioButton themePrimierDark;

    public void switchToMainMenu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void getTheme(ActionEvent event) {
        if(themeDracula.isSelected()) {
            Application.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());
        }
        else if(themeNordDark.isSelected()) {
            Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
        }
        else if(themePrimierDark.isSelected()) {
            Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        }
    }
}
