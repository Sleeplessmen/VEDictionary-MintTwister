import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import base.*;
import java.util.Random;

public class QuizController {
    @FXML
    private Label questionLabel;

    @FXML
    private Button choiceA;

    @FXML
    private Button choiceB;

    @FXML
    private Button choiceC;

    @FXML
    private Button choiceD;

    private QuizGame quizGame;
    private int currentQuestionIndex;

    @FXML
    public void initialize() {
        quizGame = new QuizGame();
        quizGame.readquestionsfromFile();
        currentQuestionIndex = -1;
        showNextQuestion();
    }

    private void showNextQuestion() {
        currentQuestionIndex++;

        if (currentQuestionIndex < quizGame.getSize()) {
            QuizGame.Question currentQuestion = quizGame.getQuestions().get(currentQuestionIndex);
            questionLabel.setText(currentQuestion.prompt());
            enableButtons();
        } else {
            // No more questions, handle end of the quiz
            questionLabel.setText("Quiz completed!");
            disableButtons();
        }
    }

    private void enableButtons() {
        choiceA.setDisable(false);
        choiceB.setDisable(false);
        choiceC.setDisable(false);
        choiceD.setDisable(false);
    }

    private void disableButtons() {
        choiceA.setDisable(true);
        choiceB.setDisable(true);
        choiceC.setDisable(true);
        choiceD.setDisable(true);
    }

    @FXML
    private void handleChoiceClick(javafx.event.ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String userAnswer = clickedButton.getText();

        QuizGame.Question currentQuestion = quizGame.getQuestions().get(currentQuestionIndex);
        String correctAnswer = currentQuestion.answer().toLowerCase();

        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            questionLabel.setText("Correct answer!");
        } else {
            questionLabel.setText("Wrong answer. The correct answer is: " + correctAnswer);
        }
        disableButtons();
    }

    @FXML
    private void handleContinueClick() {
        showNextQuestion();
    }
}

