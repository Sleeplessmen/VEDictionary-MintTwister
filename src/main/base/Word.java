package base;

import java.util.List;

public class Word implements Comparable<Word> {
    private String wordTarget;
    private String wordExplain;
    private String wordPronunciation;
    public Word(String wordTarget, String wordExplain)
    {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    public Word(String wordTarget, String wordExplain, String wordPronunciation)
    {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
        this.wordPronunciation = wordPronunciation;
    }

    public Word() {

    }

    public void setWordExplain(String wordTarget) {
        wordExplain = wordTarget;
    }

    public String getWordTarget() {
        return this.wordTarget;
    }

    public String getWordExplain() {
        return this.wordExplain;
    }

    public String getWordPronunciation() {
        return wordPronunciation;
    }

    public void setWordPronunciation(String wordPronunciation) {
        this.wordPronunciation = wordPronunciation;
    }
    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    @Override
    public int compareTo(Word o) {
        return this.wordTarget.compareTo(o.getWordTarget());
    }

    public void appendExplanation(String additionalExplanation) {
        if (wordExplain == null) {
            wordExplain = additionalExplanation;
        } else {
            wordExplain += " " + additionalExplanation;
        }
    }
}
