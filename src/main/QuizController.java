import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import base.*;

import java.util.*;

public class QuizController extends QuizGameTemplate {
    private int currentQuestionIndex;
    private int streak = 0;
    private Set<Integer> answeredQuestions;
    @FXML
    public void initialize() {
        quizGame = new QuizGame();
        quizGame.readquestionsfromFile();
        currentQuestionIndex = -1;
        answeredQuestions = new HashSet<>();
        showNextQuestion();
    }

    private void showNextQuestion() {
        if (answeredQuestions.size() == quizGame.getSize()) {
            questionLabel.setText("Quiz completed!");
            disableButtons();
            return;
        }
        Set<Integer> unansweredQuestions = new HashSet<>(quizGame.getSize());
        for (int i = 0; i < quizGame.getSize(); i++) {
            if (!answeredQuestions.contains(i)) {
                unansweredQuestions.add(i);
            }
        }
        Integer[] unansweredArray = unansweredQuestions.toArray(new Integer[0]);
        Collections.shuffle(Arrays.asList(unansweredArray));
        currentQuestionIndex = unansweredArray[0];
        QuizGame.Question currentQuestion = quizGame.getQuestions().get(currentQuestionIndex);
        questionLabel.setText(currentQuestion.prompt());
        enableButtons();
    }



    @FXML
    public void handleChoiceClick(javafx.event.ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String userAnswer = clickedButton.getText();

        QuizGame.Question currentQuestion = quizGame.getQuestions().get(currentQuestionIndex);
        String correctAnswer = currentQuestion.answer().toLowerCase();

        if (userAnswer.equalsIgnoreCase(correctAnswer)) {
            questionLabel.setText("Correct answer!");
            streak += 1;
            currentStreak.setText("Answer Streak: " + streak);
            answeredQuestions.add(currentQuestionIndex);
        } else {
            questionLabel.setText("Wrong answer. The correct answer is: " + correctAnswer);
            streak = 0;
            currentStreak.setText("Answer Streak: 0");
            answeredQuestions.clear();
        }
        disableButtons();
    }

    @Override
    public void handleContinueClick(ActionEvent event) {
        showNextQuestion();
    }
}

