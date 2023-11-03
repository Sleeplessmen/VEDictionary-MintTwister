public class Word implements Comparable<Word> {
    private String wordTarget;
    private String wordExplain;

    public Word() {}

    public Word(String wordTarget, String wordExplain)
    {
        this.wordTarget = wordTarget;
        this.wordExplain = wordExplain;
    }

    public void setWordTarget(String wordExplain) {
        wordTarget = wordExplain;
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

    @Override
    public int compareTo(Word o) {
        return this.wordTarget.compareTo(o.getWordTarget());
    }
}
