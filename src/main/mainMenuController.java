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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import base.*;
public class mainMenuController implements Initializable {
    private Parent settingParent;
    protected static Dictionary dictionary = new Dictionary();
    protected DBDictionary dbDictionary;
    protected ObservableList<Word> allWords = FXCollections.observableArrayList();
    private FilteredList<Word> filteredWords;
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private MenuItem importFile, exportFile;
    @FXML
    protected static ImageView bgImage = new ImageView();
    @FXML
    private Button addWordButton, deleteWordButton, editWordButton, settingButton, gameButton;
    @FXML
    private ListView<Word> wordList;
    @FXML
    private TextArea textArea;
    @FXML
    private TextField searchField;
    @FXML
    private ImageView addIcon,deleteIcon,editIcon,ggIcon,settingIcon,gameIcon;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setIcons(DictApplication.isDarkMode());
        dbDictionary = new DBDictionary();
        try {
            dbDictionary.initialize();
            allWords = dbDictionary.getAllWordsFromDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

                textArea.setText("Pronunciation: " + pronunciation + "\nExplanation: " + explanation);
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchDictionary();
        });
    }
    public void setDBDictionary(DBDictionary dbDictionary) {
        this.dbDictionary = dbDictionary;
    }
    public ObservableList<Word> insertFromFile() {
        ObservableList<Word> wordList = FXCollections.observableArrayList();
        String wordTarget = "";
        String wordExplain = "";
        String wordPronunciation = "";

        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\cusnaruto\\Downloads\\Uni Stuffs\\OOP\\BigProject\\VEDictionary-MintTwister\\src\\resources\\anhviet109K.txt"))) {
            String line;
            Word currentWord = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("@")) {
                    wordTarget = line.substring(1).trim();

                    String[] parts = wordTarget.split("/");
                    if (parts.length > 1) {
                        wordTarget = parts[0].trim();
                        wordPronunciation = "/" + parts[1].trim() + "/"; // Keep the "/word/" format
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
                    // Add the word to the wordList
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
        File selectedFile = fileChooser.showOpenDialog(stage);
    }

    @FXML
    void importFromFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);
    }
    public mainMenuController() {
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
        settingStage.setScene(new Scene(root));
        settingStage.show();
    }
    @FXML
    void searchDictionary() {
        String searchKeyword = searchField.getText().toLowerCase();
        filteredWords.setPredicate(word ->
                word.getWordTarget().toLowerCase().startsWith(searchKeyword));
    }
    private void setIcons(boolean isDarkMode) {
        if (isDarkMode) {
            addIcon = new ImageView(new Image("/addIcon.png"));
            deleteIcon = new ImageView(new Image("/deleteIcon.png"));
            editIcon = new ImageView(new Image("/editIcon.png"));
            ggIcon = new ImageView(new Image("/ggTransIcon.png"));
            settingIcon = new ImageView(new Image("/settingIcon.png"));

        } else {
            addIcon = new ImageView(new Image("/darkAddIcon.png"));
            deleteIcon = new ImageView(new Image("/darkDeleteIcon.png"));
            editIcon = new ImageView(new Image("/darkEditIcon.png"));
            ggIcon = new ImageView(new Image("/darkGGTransIcon.png"));
            settingIcon = new ImageView(new Image("/darkSettingIcon.png"));
        }
    }

    @FXML
    void launchHangMan(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HangmanController.fxml"));
        Parent root = loader.load();
        HangmanApp hangmanController = loader.getController();
        Stage newStage = new Stage();
        newStage.setWidth(600);
        newStage.setHeight(450);
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setTitle("Hangman Game");
        newStage.show();

    }

    @FXML
    void addWord(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Word");
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

            Word newWord = new Word(wordText, explanation, pronunciation);
            Dictionary.listWord.add(newWord);
            allWords.add(newWord);
        });
    }

    @FXML
    void deleteWord(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Word");
        dialog.setHeaderText("Enter Word");

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
                System.out.println(w.getWordTarget());
                if (wordText.equalsIgnoreCase(w.getWordTarget())) {
                    wordFound = true;
                    Dictionary.listWord.remove(w);
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
                System.out.println(w.getWordTarget());
                if (wordText.equalsIgnoreCase(w.getWordTarget())) {
                    if (pronunciation.isEmpty() && explanation.isEmpty()) {
                        wordFound = true;
                        break;
                    }
                    else if (!pronunciation.isEmpty() && explanation.isEmpty()) {
                        wordFound = true;
                        w.setWordPronunciation(pronunciation);
                        break;
                    }
                    else if (pronunciation.isEmpty() && !explanation.isEmpty()) {
                        wordFound = true;
                        w.setWordExplain(explanation);
                        break;
                    }
                    else {
                        wordFound = true;
                        w.setWordExplain(explanation);
                        w.setWordPronunciation(pronunciation);
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

    public void stop() {
        // Close the database connection
        if (dbDictionary != null) {
            dbDictionary.closeConnection();
        }
    }

}

