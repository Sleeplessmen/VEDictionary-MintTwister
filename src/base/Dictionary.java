import java.util.ArrayList;

public class Dictionary {

    private ArrayList<Word> words;

    public Dictionary() {
        words = new ArrayList<>();
    }

    public void addWord(Word word) {
        words.add(word);
    }

    public void removeWord(String wordTarget) {
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getWordTarget().equals(wordTarget)) {
                words.remove(i);
                break;
            }
        }
    }

    public void updateWord(String wordTarget, String wordExplain) {
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getWordTarget().equals(wordTarget)) {
                words.get(i).setWordExplain(wordExplain);
                break;
            }
        }
    }

    public Word findWord(String wordTarget) {
        for (Word word : words) {
            if (word.getWordTarget().equals(wordTarget)) {
                return word;
            }
        }
        return null;
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "words=" + words +
                '}';
    }
}