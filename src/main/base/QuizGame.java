package base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class QuizGame {
    public static final String MCQ = "src/resources/output.txt";

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
                // Split the line into parts using tab as a separator
                String[] parts = line.split("\t");

                // The prompt is everything before the last part
                String prompt = String.join("\n", Arrays.copyOfRange(parts, 0, parts.length - 1));

                // The answer is the last part
                String answer = parts[parts.length - 1];

                Question question = new Question(prompt, answer);
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