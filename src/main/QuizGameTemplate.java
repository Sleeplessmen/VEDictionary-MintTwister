import base.QuizGame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public abstract class QuizGameTemplate {
    protected QuizGame quizGame;
    @FXML
    public Button choiceA;

    @FXML
    public Button choiceB;

    @FXML
    protected Button choiceC;

    @FXML
    protected Button choiceD;

    @FXML
    protected Label currentStreak;

    @FXML
    protected Label questionLabel;

    @FXML
    public abstract void initialize();

    @FXML
    public abstract void handleChoiceClick(ActionEvent event);

    @FXML
    public abstract void handleContinueClick(ActionEvent event);

    protected void enableButtons() {
        choiceA.setDisable(false);
        choiceB.setDisable(false);
        choiceC.setDisable(false);
        choiceD.setDisable(false);
    }

    protected void disableButtons() {
        choiceA.setDisable(true);
        choiceB.setDisable(true);
        choiceC.setDisable(true);
        choiceD.setDisable(true);
    }
}
