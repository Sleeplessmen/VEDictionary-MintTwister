import base.Dictionary;
import com.sun.javafx.tk.TKStage;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import base.*;
public class mainMenuController implements Initializable {
    private Parent settingParent;
    protected ObservableList<Word> allWords = FXCollections.observableArrayList();
    private FilteredList<Word> filteredWords;
    private String currentlySelectedWord;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private MenuItem importFile, exportFile, aboutItem;
    @FXML
    protected static ImageView bgImage = new ImageView();
    @FXML
    private Button addWordButton, deleteWordButton, editWordButton, settingButton, gameButton, ttsButton, translateButton, addButton, addToFavouriteButton;
    @FXML
    private ListView<Word> wordList;
    @FXML
    private CheckBox favouriteCheckBox;
    @FXML
    private TextArea textArea;
    @FXML
    private TextField searchField, ggSearchField;
    @FXML
    public static ImageView addIcon,deleteIcon,editIcon,ggIcon,settingIcon,gameIcon;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allWords = DictApplication.dbDictionary.getAllWordsFromDB();
        filteredWords = new FilteredList<>(allWords);
        wordList.setItems(filteredWords);
        wordList.setCellFactory(param -> new ListCell<Word>() {
            @Override
            protected void updateItem(Word item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getWordTarget());
                }
            }
        });
        wordList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedWord) -> {
            if (selectedWord != null) {
                String pronunciation = selectedWord.getWordPronunciation();
                String explanation = selectedWord.getWordExplain();
                currentlySelectedWord = selectedWord.getWordTarget();
                textArea.setText("Pronunciation: " + "/" + pronunciation + "/" + "\nExplanation: " + explanation);
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchDictionary();
        });
    }
    public ObservableList<Word> insertFromFile(String path) {
        ObservableList<Word> wordList = FXCollections.observableArrayList();
        String wordTarget = "";
        String wordExplain = "";
        String wordPronunciation = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            Word currentWord = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("@")) {
                    wordTarget = line.substring(1).trim();

                    String[] parts = wordTarget.split("/");
                    if (parts.length > 1) {
                        wordTarget = parts[0].trim();
                        wordPronunciation = "/" + parts[1].trim() + "/";
                    } else {
                        wordPronunciation = "";
                    }

                    currentWord = new Word(wordTarget, "", wordPronunciation);
                } else if (line.startsWith("*")) {
                    String lineContents = line.substring(1).trim();
                    int pronunciationStart = lineContents.indexOf('/');
                    if (pronunciationStart != -1) {
                        int pronunciationEnd = lineContents.lastIndexOf('/');
                        if (pronunciationEnd > pronunciationStart) {
                            wordExplain = lineContents.substring(0, pronunciationStart).trim();
                            wordPronunciation = "/" + lineContents.substring(pronunciationStart + 1, pronunciationEnd).trim() + "/"; // Keep the "/word/" format
                        } else {
                            wordExplain = lineContents;
                        }
                    } else {
                        wordExplain = lineContents;
                    }

                    if (currentWord != null) {
                        currentWord.appendExplanation(wordExplain);
                    }
                } else if (line.startsWith("-")) {
                    wordExplain += " " + line.substring(1).trim();
                } else if (!line.isEmpty()) {
                    if (currentWord != null) {
                        currentWord.setWordExplain(wordExplain);
                        wordList.add(currentWord);
                    }

                    wordTarget = "";
                    wordExplain = "";
                    wordPronunciation = "";
                    currentWord = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordList;
    }

    @FXML
    void exportToFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("output.txt");
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            export(file);
        }
    }

    private void export(File file) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            String format = "%-15s %-20s %-15s%n";
            for (Word word : allWords) {
                bufferedWriter.write(String.format(format, word.getWordTarget(), "/" + word.getWordPronunciation() + "/", word.getWordExplain()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void importFromFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            allWords = insertFromFile(selectedFile.getPath());
            filteredWords = new FilteredList<>(allWords);
            wordList.setItems(filteredWords);
        }
    }
    public mainMenuController() {
    }

    @FXML
    void readAloud(ActionEvent event) throws Exception {
        TextToSpeech.generate(currentlySelectedWord);
        TextToSpeech.playWav("voice.wav");
    }

    public void logout(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exiting...");
        alert.setHeaderText("Bạn đang muốn thoát?");
        alert.setContentText("Nếu có, hãy bấm OK");

        if(alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) scenePane.getScene().getWindow();
            System.out.println("le logout had arrived");
            stage.close();
        }

    }

    public void switchToSetting(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("settingScene.fxml"));
        Parent root = loader.load();
        settingController settingController = loader.getController();
        settingController.setParentController(this);
        Stage settingStage = new Stage();
        settingStage.setTitle("Settings");
        settingStage.setWidth(800);
        settingStage.setHeight(600);
        settingStage.setResizable(false);
        settingStage.setScene(new Scene(root));
        settingStage.show();
    }
    @FXML
    void searchDictionary() {
        String searchKeyword = searchField.getText().toLowerCase();
        filteredWords.setPredicate(word ->
                word.getWordTarget().toLowerCase().startsWith(searchKeyword));
    }

    @FXML
    void addWord(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Word/Phrase");
        dialog.setHeaderText("Enter Word Information");

        TextField wordTargetField = new TextField();
        wordTargetField.setPromptText("Word");
        wordTargetField.setPrefWidth(200);

        TextField pronunciationField = new TextField();
        pronunciationField.setPromptText("Pronunciation");

        TextArea explanationField = new TextArea();
        explanationField.setPromptText("Explanation");

        GridPane grid = new GridPane();
        grid.add(new Label("Word:"), 0, 0);
        grid.add(wordTargetField, 1, 0);
        grid.add(new Label("Pronunciation:"), 0, 1);
        grid.add(pronunciationField, 1, 1);
        grid.add(new Label("Explanation:"), 0, 2);
        grid.add(explanationField, 1, 2);
        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(word -> {
            String wordText = wordTargetField.getText();
            String pronunciation = pronunciationField.getText();
            String explanation = explanationField.getText();
            if (DictApplication.dbDictionary.lookupWord(wordText) == "N/A") {
                Word newWord = new Word(wordText, explanation, pronunciation);
                allWords.add(newWord);
                DictApplication.dbDictionary.insertWord(wordText, pronunciation, explanation);
            }
        });
    }

    @FXML
    void deleteWord(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Word");
        dialog.setHeaderText("Enter Word/Phrase");

        TextField wordTargetField = new TextField();
        wordTargetField.setPromptText("Word");
        wordTargetField.setPrefWidth(200);

        GridPane grid = new GridPane();
        grid.add(new Label("Word:"), 0, 0);
        grid.add(wordTargetField, 1, 0);
        dialog.getDialogPane().setContent(grid);
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(word -> {
            String wordText = wordTargetField.getText();
            boolean wordFound = false;
            for (Word w : allWords) {
                if (wordText.equalsIgnoreCase(w.getWordTarget())) {
                    wordFound = true;
                    DictApplication.dbDictionary.removeWord(wordText);
                    allWords.remove(w);
                    break;
                }
            }
            if (!wordFound) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Word doesn't exist");
                errorAlert.showAndWait();
            }
        });
    }

    @FXML
    void editWord(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Edit Word");
        dialog.setHeaderText("Enter Word Information");

        TextField wordTargetField = new TextField();
        wordTargetField.setPromptText("Word");
        wordTargetField.setPrefWidth(200);

        TextField pronunciationField = new TextField();
        pronunciationField.setPromptText("New Pronunciation");

        TextArea explanationField = new TextArea();
        explanationField.setPromptText("New Explanation");

        GridPane grid = new GridPane();
        grid.add(new Label("Word:"), 0, 0);
        grid.add(wordTargetField, 1, 0);
        grid.add(new Label("Pronunciation:"), 0, 1);
        grid.add(pronunciationField, 1, 1);
        grid.add(new Label("Explanation:"), 0, 2);
        grid.add(explanationField, 1, 2);
        dialog.getDialogPane().setContent(grid);

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(word -> {
            String wordText = wordTargetField.getText();
            String pronunciation = pronunciationField.getText();
            String explanation = explanationField.getText();
            boolean wordFound = false;
            for (Word w : allWords) {
                if (wordText.equalsIgnoreCase(w.getWordTarget())) {
                    if (pronunciation.isEmpty() && explanation.isEmpty()) {
                        wordFound = true;
                        break;
                    }
                    else if (!pronunciation.isEmpty() && explanation.isEmpty()) {
                        wordFound = true;
                        w.setWordPronunciation(pronunciation);
                        DictApplication.dbDictionary.modifyWord(wordText,pronunciation,w.getWordExplain());
                        break;
                    }
                    else if (pronunciation.isEmpty() && !explanation.isEmpty()) {
                        wordFound = true;
                        w.setWordExplain(explanation);
                        DictApplication.dbDictionary.modifyWord(wordText,w.getWordPronunciation(),explanation);
                        break;
                    }
                    else {
                        wordFound = true;
                        w.setWordExplain(explanation);
                        w.setWordPronunciation(pronunciation);
                        DictApplication.dbDictionary.modifyWord(wordText, pronunciation, explanation);
                        break;
                    }
                }
            }
            if (!wordFound) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("Word doesn't exist");
                errorAlert.showAndWait();
            }
        });
    }
    @FXML
    void translate(ActionEvent event) throws IOException {
        String textToTranslate = ggSearchField.getText();
        String translatedText = Translator.translate(textToTranslate);
        currentlySelectedWord = textToTranslate;
        textArea.setText(translatedText);
        addButton = new Button("Add this word/phrase");
        AnchorPane.setTopAnchor(addButton, 53.0);
        AnchorPane.setLeftAnchor(addButton, 641.0);
        addButton.setOnAction(addEvent -> {
            if (addButton != null) {
                anchorPane.getChildren().remove(addButton);
                addButton = null;
            }
            if (!check(textToTranslate)) {
                Word newWord = new Word(textToTranslate, translatedText, null);
                DictApplication.dbDictionary.insertWord(textToTranslate, null, translatedText);
                allWords.add(newWord);
            }
        });
        anchorPane.getChildren().add(addButton);
    }
    public boolean check(String n) {
        for (Word w : allWords) {
            if (n.equalsIgnoreCase(w.getWordTarget())) {
                return true;
            }
        }
        return false;
    }
    @FXML
    private void showGameDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Choose a Game");
        dialog.setHeaderText("Select a game to play:");

        ButtonType game1Button = new ButtonType("Hangman");
        ButtonType game2Button = new ButtonType("Quiz Time!");
        ButtonType game3Button = new ButtonType("Word Quiz");

        dialog.getDialogPane().getButtonTypes().addAll(game1Button, game2Button, game3Button, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == game1Button) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("HangmanController.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                HangmanApp hangmanController = loader.getController();
                Stage newStage = new Stage();
                newStage.setWidth(600);
                newStage.setHeight(450);
                newStage.setResizable(false);
                Scene scene = new Scene(root);
                newStage.setScene(scene);
                newStage.setTitle("Hangman Game");
                newStage.show();
                System.out.println("Launching Hangman");
            } else if (buttonType == game2Button) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizScene.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                QuizController quizController = loader.getController();
                Stage newStage = new Stage();
                newStage.setWidth(800);
                newStage.setHeight(600);
                newStage.setResizable(false);
                Scene scene = new Scene(root);
                newStage.setScene(scene);
                newStage.setTitle("Quiz Time");
                newStage.show();
                System.out.println("Launching Quiz Time");
            }
            else if (buttonType == game3Button) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("WordQuizScene.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                WordQuizController wordQuizController = loader.getController();
                Stage newStage = new Stage();
                newStage.setWidth(800);
                newStage.setHeight(600);
                newStage.setResizable(false);
                Scene scene = new Scene(root);
                newStage.setScene(scene);
                newStage.setTitle("Word Quiz");
                newStage.show();
                System.out.println("Launching Word Quiz");
            }
            return null;
        });
        dialog.showAndWait();
    }

    @FXML
    private void showFavourites(ActionEvent event) {
        List<Word> words = getWordsFromDatabase(favouriteCheckBox.isSelected());
        allWords.setAll(words);
    }

    private List<Word> getWordsFromDatabase(boolean showFavourites) {
        String query = "SELECT Word, Pronunciation, Explanation FROM dictionary WHERE Favourite = ?";

        try (PreparedStatement ps = DictApplication.dbDictionary.con.prepareStatement(query)) {
            ps.setInt(1, showFavourites ? 1 : 0);
            return getWordsFromResultSet(ps);
        } catch (SQLException e) {
            e.printStackTrace(); // Consider logging or handling the exception appropriately
        }

        return new ArrayList<>();
    }

    private List<Word> getWordsFromResultSet(PreparedStatement ps) throws SQLException {
        List<Word> words = new ArrayList<>();

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String word = rs.getString("Word");
                String pronunciation = rs.getString("Pronunciation");
                String explanation = rs.getString("Explanation");

                // Assuming you have a Word constructor
                Word currentWord = new Word(word, explanation, pronunciation);
                words.add(currentWord);
            }
        }
        return words;
    }

    @FXML
    public boolean toggleFavourite(ActionEvent event) {
        String query = "UPDATE dictionary SET Favourite = CASE WHEN Favourite = 1 THEN 0 ELSE 1 END WHERE Word = ?";
        String wordTarget = currentlySelectedWord;
        try (PreparedStatement ps = DictApplication.dbDictionary.con.prepareStatement(query)) {
            ps.setString(1, wordTarget);

            if (ps.executeUpdate() == 0) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @FXML
    void showAbout(ActionEvent event) {
        Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
        aboutDialog.setTitle("About");
        aboutDialog.setHeaderText("MintTwister");
        aboutDialog.setContentText("An English learning application, made as an OOP project, by Le Cong Hoang, Nguyen Cong Khai and Do Hoai Nam");
        aboutDialog.showAndWait();
    }
}

