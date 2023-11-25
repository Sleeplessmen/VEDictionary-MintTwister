import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import base.*;

public class WordQuizController extends QuizGameTemplate {
    List<Word> wordList = DictApplication.dbDictionary.getAllWordsFromDb();
    private int currentQuestionIndex;
    private String currentAnswer;
    private int streak;

    @Override
    public void initialize() {
        showNextQuestion();
    }

    @Override
    public void handleChoiceClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String userAnswer = clickedButton.getText();

        String correctAnswer = currentAnswer;

        if (userAnswer.equals(correctAnswer)) {
            questionLabel.setText("Correct answer!");
            streak++;
            currentStreak.setText("Answer Streak: " + streak);
        } else {
            questionLabel.setText("Wrong answer. The correct answer is: " + correctAnswer);
            streak = 0;
            currentStreak.setText("Answer Streak: " + streak);
        }

        disableButtons();
    }

    private static class QuestionAndOptions {
        private final String question;
        private final List<String> answerOptions;

        public QuestionAndOptions(String question, List<String> answerOptions) {
            this.question = question;
            this.answerOptions = answerOptions;
        }

        public String getQuestion() {
            return question;
        }

        public List<String> getAnswerOptions() {
            return answerOptions;
        }
    }

    private QuestionAndOptions generateRandomQuestion() {
        if (wordList.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(wordList.size());
        Word randomWord = wordList.get(randomIndex);
        String question = randomWord.getWordExplain();
        String correctAnswer = randomWord.getWordTarget();
        currentAnswer = correctAnswer;
        List<String> incorrectAnswers = getThreeIncorrectAnswers(correctAnswer);
        List<String> answerOptions = new ArrayList<>();
        answerOptions.add(correctAnswer);
        answerOptions.addAll(incorrectAnswers);
        Collections.shuffle(answerOptions);
        return new QuestionAndOptions(question, answerOptions);
    }

    private List<String> getThreeIncorrectAnswers(String correctAnswer) {
        List<String> incorrectAnswers = new ArrayList<>();
        List<Word> allWords = DictApplication.dbDictionary.getAllWordsFromDb();
        Collections.shuffle(allWords);
        for (Word word : allWords) {
            if (!word.getWordTarget().equals(correctAnswer)) {
                incorrectAnswers.add(word.getWordTarget());
            }
            if (incorrectAnswers.size() == 3) {
                break;
            }
        }
        return incorrectAnswers;
    }

    @FXML
    public void handleContinueClick(ActionEvent event) {
        showNextQuestion();
    }

    private void showNextQuestion() {
        currentQuestionIndex++;

        if (currentQuestionIndex < wordList.size()) {
            QuestionAndOptions questionAndOptions = generateRandomQuestion();
            questionLabel.setText(questionAndOptions.getQuestion());
            List<String> answerOptions = questionAndOptions.getAnswerOptions();
            choiceA.setText(answerOptions.get(0));
            choiceB.setText(answerOptions.get(1));
            choiceC.setText(answerOptions.get(2));
            choiceD.setText(answerOptions.get(3));

            enableButtons();
        } else {
            questionLabel.setText("Quiz completed!");
            disableButtons();
        }
    }

    private void showFeedback(String feedback) {
        questionLabel.setText(feedback);
    }

    public String getCorrectAnswer(int questionIndex) {
        if (questionIndex >= 0 && questionIndex < wordList.size()) {
            QuestionAndOptions questionAndOptions = generateRandomQuestion();
            return questionAndOptions.getAnswerOptions().get(0);
        } else {
            return null;
        }
    }

}
