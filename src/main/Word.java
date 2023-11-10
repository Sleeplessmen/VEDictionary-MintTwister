import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Word {
    private String word_target;
    private String word_explain;
    private String word_pronunciation;

    private final StringProperty wordTarget = new SimpleStringProperty();
    private final StringProperty wordPronunciation = new SimpleStringProperty();
    private final StringProperty wordExplain = new SimpleStringProperty();

    public Word() {}

    public Word(String wt, String we, String pr) {
        word_target = wt;
        word_explain = we;
        word_pronunciation = pr;
    }

    public Word(String wordTarget, String[] wordExplains) {
    }

    public void setWord_target(String wt) {
        word_target = wt;
    }

    public String getWord_pronunciation() {
        return word_pronunciation;
    }

    public void setWord_pronounciation(String word_pronunciation) {
        this.word_pronunciation = word_pronunciation;
    }

    public void setWord_explain(String we) {
        word_explain = we;
    }

    public String getWord_target() {
        return this.word_target;
    }

    public String getWord_explain() {
        return this.word_explain;
    }

    @Override
    public String toString() {
        return getWord_target();
    }

    public void appendExplanation(String additionalExplanation) {
        if (word_explain == null) {
            word_explain = additionalExplanation;
        } else {
            word_explain += " " + additionalExplanation;
        }
    }

    public StringProperty wordTargetProperty() {
        return wordTarget;
    }

    public StringProperty wordPronunciationProperty() {
        return wordPronunciation;
    }

    public StringProperty wordExplainProperty() {
        return wordExplain;
    }
}
