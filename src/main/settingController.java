
import atlantafx.base.theme.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class settingController implements Initializable {
    private mainMenuController mainMenu;
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ToggleGroup Themes;

    @FXML
    private Button backButton;

    @FXML
    private RadioButton themeDracula, themeNordDark,themePrimierDark, themeCupertinoDark,
            themeNordLight,themePrimierLight, themeCupertinoLight;

    public void switchToMainMenu(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
            Parent parent = fxmlLoader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        } catch (IOException e) {
            System.err.println("loi roi em");
            throw new RuntimeException(e);
        }
    }
    @FXML
    void getTheme(ActionEvent event) {
        if (themeDracula.isSelected()) {
            Application.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());
            DictApplication.setDarkMode(true);
        } else if (themeNordDark.isSelected()) {
            Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
            DictApplication.setDarkMode(true);
        } else if (themePrimierDark.isSelected()) {
            Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
            DictApplication.setDarkMode(true);
        } else if (themeCupertinoDark.isSelected()) {
            Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
            DictApplication.setDarkMode(true);
        } else if (themeNordLight.isSelected()) {
            Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());
            DictApplication.setDarkMode(true);
        } else if (themePrimierLight.isSelected()) {
            Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
            DictApplication.setDarkMode(true);
        } else if (themeCupertinoLight.isSelected()) {
            Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());
            DictApplication.setDarkMode(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
