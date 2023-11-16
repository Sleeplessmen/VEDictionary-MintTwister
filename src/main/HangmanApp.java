import javafx.application.Application;
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
import java.util.ResourceBundle;

public class HangmanApp extends Application implements Initializable {
    private String secretWord = "HANGMAN";
    private StringBuilder currentWordState;
    private int incorrectGuesses = 0;
    private static final int MAX_INCORRECT_GUESSES = 6;
    @FXML
    private Label currentStateOfWord;

    @FXML
    private Button guessButton;

    @FXML
    private ImageView hangmanImage;

    @FXML
    private Button hintButton;

    @FXML
    private Label incorrectCharacters;

    @FXML
    private TextField textField;
    @FXML
    private Label messageLabel;

    @FXML
    void giveHint(ActionEvent event) {

    }

    @FXML
    void handleGuess(ActionEvent event) {
        String guess = textField.getText().toUpperCase();
        if (guess.length() == 1 && Character.isLetter(guess.charAt(0))) {
            char guessedLetter = guess.charAt(0);
            boolean correctGuess = false;

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
            messageLabel.setText("Invalid guess. Please enter a single letter.");
        }
        textField.clear();
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
        currentWordState = new StringBuilder();
        for (int i = 0; i < secretWord.length(); i++) {
            currentWordState.append("_");
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
