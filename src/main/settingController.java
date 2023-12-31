
import atlantafx.base.theme.*;
import base.TextToSpeech;
import com.voicerss.tts.Languages;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
public class settingController implements Initializable {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ToggleGroup Themes;

    @FXML
    private RadioButton themeDracula, themeNordDark,themePrimierDark, themeCupertinoDark,
            themeNordLight,themePrimierLight, themeCupertinoLight;

    @FXML
    private RadioButton voiceENUS, voiceENUK, voiceENAUS, voiceENCA,voiceENIN,voiceENIRL;
    private mainMenuController controller;
    public void setParentController(mainMenuController controller) {
        this.controller = controller;
    }
    @FXML
    void getVoice(ActionEvent event) {
        if (voiceENUS.isSelected()) {
            TextToSpeech.setVoice(Languages.English_UnitedStates);
        } else if (voiceENUK.isSelected()) {
            TextToSpeech.setVoice(Languages.English_GreatBritain);
        } else if (voiceENAUS.isSelected()) {
            TextToSpeech.setVoice(Languages.English_Australia);
        } else if (voiceENCA.isSelected()) {
            TextToSpeech.setVoice(Languages.English_Canada);
        } else if (voiceENIN.isSelected()) {
            TextToSpeech.setVoice(Languages.English_India);
        } else if (voiceENIRL.isSelected()) {
            TextToSpeech.setVoice(Languages.English_Ireland);
        }
    }
    @FXML
    void getTheme(ActionEvent event) {
        if (themeDracula.isSelected()) {
            Application.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());
        } else if (themeNordDark.isSelected()) {
            Application.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
        } else if (themePrimierDark.isSelected()) {
            Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        } else if (themeCupertinoDark.isSelected()) {
            Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
        } else if (themeNordLight.isSelected()) {
            Application.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());
        } else if (themePrimierLight.isSelected()) {
            Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
        } else if (themeCupertinoLight.isSelected()) {
            Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}