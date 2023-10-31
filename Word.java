public class Word {
    private String word_target;
    private String word_explain;

    public Word() {}

    public Word(String wt, String we) {
        word_target = wt;
        word_explain = we;
    }

    public void setWord_target(String wt) {
        word_target = wt;
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
}
