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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class mainMenuController implements Initializable {
    protected static Dictionary dictionary = new Dictionary();
    private ObservableList<Word> allWords;
    private FilteredList<Word> filteredWords;
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private settingController settingController;
    @FXML
    private AnchorPane scenePane;
    @FXML
    private MenuItem exportFile;
    @FXML
    private Button settingButton;
    @FXML
    private MenuItem importFile;
    @FXML
    private ImageView bgImage;
    @FXML
    private Button addWordButton;
    @FXML
    private ListView<Word> wordList;
    @FXML
    private TextArea textArea;
    @FXML
    private TextField searchField;
    @FXML
    private ImageView addIcon,deleteIcon,editIcon,ggIcon,settingIcon;


    public ObservableList<Word> insertFromFile() {
        ObservableList<Word> wordList = FXCollections.observableArrayList();
        String wordTarget = "";
        String wordExplain = "";
        String wordPronunciation = "";

        try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\cusnaruto\\Downloads\\Uni Stuffs\\OOP\\BigProject\\VEDictionary-MintTwister\\src\\resources\\anhviet109K.txt"))) {
            String line;
            Word currentWord = null; // Initialize currentWord as null

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("@")) {
                    // Extract the word target
                    wordTarget = line.substring(1).trim();

                    // Extract the pronunciation if present
                    String[] parts = wordTarget.split("/");
                    if (parts.length > 1) {
                        wordTarget = parts[0].trim();
                        wordPronunciation = parts[1].trim();
                    } else {
                        wordPronunciation = "";
                    }

                    // Create a new Word object
                    currentWord = new Word(wordTarget, "", wordPronunciation);
                } else if (line.startsWith("*")) {
                    // Extract the word explanation
                    String lineContents = line.substring(1).trim();
                    int pronunciationStart = lineContents.indexOf('/');
                    if (pronunciationStart != -1) {
                        int pronunciationEnd = lineContents.lastIndexOf('/');
                        if (pronunciationEnd > pronunciationStart) {
                            wordExplain = lineContents.substring(0, pronunciationStart).trim();
                            wordPronunciation = lineContents.substring(pronunciationStart + 1, pronunciationEnd).trim();
                        } else {
                            wordExplain = lineContents;
                        }
                    } else {
                        wordExplain = lineContents;
                    }

                    // Append the explanation to the currentWord
                    if (currentWord != null) {
                        currentWord.appendExplanation(wordExplain);
                    }
                } else if (line.startsWith("-")) {
                    // Append the word meaning to the word explanation
                    wordExplain += " " + line.substring(1).trim();
                } else if (!line.isEmpty()) {
                    // Add the word to the wordList
                    if (currentWord != null) {
                        currentWord.setWord_explain(wordExplain);
                        wordList.add(currentWord);
                    }

                    // Reset variables for the next word
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
        Parent root = FXMLLoader.load(getClass().getResource("settingScene.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void searchDictionary() {
        // Get the search keyword from the TextField
        String searchKeyword = searchField.getText().toLowerCase();

        // Update the predicate of the filtered list
        filteredWords.setPredicate(word ->
                word.getWord_target().toLowerCase().startsWith(searchKeyword));

        // Print the matching words (for debugging)
        filteredWords.forEach(word -> System.out.println(word.getWord_target()));

        // Check if the filtered list is empty
        if (filteredWords.isEmpty()) {
            System.out.println("empty!");
        } else {
            System.out.println("not empty!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setIcons(DictApplication.isDarkMode());
        allWords = insertFromFile();
        filteredWords = new FilteredList<>(allWords);
        wordList.setItems(filteredWords);
        wordList.setCellFactory(param -> new ListCell<Word>() {
            @Override
            protected void updateItem(Word item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getWord_target());
                }
            }
        });
        wordList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedWord) -> {
            if (selectedWord != null) {
                // Retrieve the pronunciation and explanation of the selected word
                String pronunciation = selectedWord.getWord_pronunciation();
                String explanation = selectedWord.getWord_explain();

                // Update the TextArea with pronunciation and explanation
                textArea.setText("Pronunciation: " + pronunciation + "\nExplanation: " + explanation);
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Call the searchDictionary method whenever the text changes
            searchDictionary();
        });
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
    void addWord(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Word");
        dialog.setHeaderText("Enter Word Information");

        // Create input fields for word target, pronunciation, and explanation
        TextField wordTargetField = new TextField();
        wordTargetField.setPromptText("Word");
        wordTargetField.setPrefWidth(200); // Set the preferred width

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

        // Show the dialog and wait for the user's response
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(word -> {
            // Handle the input here
            String wordText = wordTargetField.getText();
            String pronunciation = pronunciationField.getText();
            String explanation = explanationField.getText();

            // Add the word to your dictionary using your own logic
            // You can create a Word object and add it to your dictionary
            Word newWord = new Word(wordText, pronunciation, explanation);
            Dictionary.listWord.add(newWord);

            wordTargetField.textProperty().bindBidirectional(newWord.wordTargetProperty());
            pronunciationField.textProperty().bindBidirectional(newWord.wordPronunciationProperty());
            explanationField.textProperty().bindBidirectional(newWord.wordExplainProperty());

        });
    }
}

