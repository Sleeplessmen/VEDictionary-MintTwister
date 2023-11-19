import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import base.*;

public class HangmanApp extends Application implements Initializable {
    private String secretWord;
    private Word currentWord;
    private StringBuilder currentWordState;
    private int incorrectGuesses = 0;
    private static final int MAX_INCORRECT_GUESSES = 6;
    @FXML
    private Label currentStateOfWord, hintText;

    @FXML
    private Button guessButton;

    @FXML
    private ImageView hangmanImage;

    @FXML
    private Button hintButton, newGameButton;

    @FXML
    private Label incorrectCharacters;

    @FXML
    private TextField textField;
    @FXML
    private Label messageLabel;
    protected ObservableList<Word> allWords = FXCollections.observableArrayList();
    private List<Word> wordList;

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
        initialize(null, null);
    }

    @FXML
    void giveHint(ActionEvent event) {
        String hint = currentWord.getWordExplain();
        hintText.setText(hint);
    }

    @FXML
    void handleGuess(ActionEvent event) {
        String guess = textField.getText().toUpperCase();
        if (guess.length() == 1) {
            char guessedLetter = guess.charAt(0);
            boolean correctGuess = false;

            if (currentWordState != null) {  // Add this check
                for (int i = 0; i < secretWord.length(); i++) {
                    if (secretWord.charAt(i) == guessedLetter) {
                        currentWordState.setCharAt(i, guessedLetter);
                        correctGuess = true;
                    }
                }

                if (!correctGuess) {
                    incorrectGuesses++;
                    updateHangmanImage();
                    incorrectCharacters.setText(incorrectCharacters.getText() + " " + guess.charAt(0));
                }

                updateUI(currentStateOfWord, messageLabel);

                if (currentWordState.toString().equals(secretWord)) {
                    messageLabel.setText("Congratulations!");
                    messageLabel.setDisable(true);
                    textField.setEditable(false);
                } else if (incorrectGuesses == MAX_INCORRECT_GUESSES) {
                    messageLabel.setText("The answer is: " + secretWord);
                    messageLabel.setDisable(true);
                    textField.setEditable(false);
                }
            } else {
                System.out.println("Error: currentWordState is not initialized.");
            }
        } else {
            messageLabel.setText("Invalid guess. Please enter a single letter.");
        }
        textField.clear();
    }

    public void resetGame() {
        incorrectGuesses = 0;
        updateHangmanImage();
        incorrectCharacters.setText("");
        textField.setEditable(true);
        messageLabel.setText("");
        initialize(null, null);
        updateUI(currentStateOfWord, messageLabel);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("HangmanController.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Hangman Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allWords = DictApplication.dbDictionary.getAllWordsFromDB();
        wordList = allWords.stream().collect(Collectors.toList());
        if (wordList != null && !wordList.isEmpty()) {
            int randomIndex = new Random().nextInt(wordList.size());
            Word randomWord = wordList.get(randomIndex);
            currentWord = randomWord;
            secretWord = randomWord.getWordTarget().toUpperCase();

            currentWordState = new StringBuilder();
            for (int i = 0; i < secretWord.length(); i++) {
                currentWordState.append("_");
            }

            updateUI(currentStateOfWord, messageLabel);  // Add this line to update the UI after initialization
        } else {
            System.out.println("Error: Word list is empty or not provided.");
        }
    }
    private void updateHangmanImage() {
        if (incorrectGuesses <= MAX_INCORRECT_GUESSES) {
            hangmanImage.setImage(new Image("hangman" + incorrectGuesses + ".png"));
        }
    }

    private void updateUI(Label currentStateOfWord, Label guessesLeft) {
        currentStateOfWord.setText(currentWordState.toString());
        guessesLeft.setText("Guesses left: " + (MAX_INCORRECT_GUESSES - incorrectGuesses));
    }

}
