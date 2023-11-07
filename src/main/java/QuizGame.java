import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class QuizGame {
    public static final String MCQ = "src/main/resources/MCQ.txt";

    private final ArrayList<Question> questions;

    public record Question(String prompt, String answer) {}

    public QuizGame() {
        questions = new ArrayList<>();
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void readquestionsfromFile() {
        try (FileReader fr = new FileReader(MCQ);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                int idx = line.lastIndexOf("\t");
                Question question = new Question(
                        line.substring(0, idx).replace('\t', '\n'), line.substring(idx + 1));
                questions.add(question);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSize() {
        return questions.size();
    }
}
