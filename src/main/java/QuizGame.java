import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class QuizGame {
    public static final String MCQ = "src/main/resources/MCQ.txt";
    private final ArrayList<Question> questions;

    public static class Question {
        private final String prompt;
        private final String answer;

        public Question(String prompt, String answer) {
            this.prompt = prompt;
            this.answer = answer;
        }

        public String getPrompt() {
            return prompt;
        }

        public String getAnswer() {
            return answer;
        }
    }

    public QuizGame() {
        questions = new ArrayList<>();
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public ArrayList<Question> readQuestionfromFile() {
        try (FileReader fr = new FileReader(MCQ);
             BufferedReader br = new BufferedReader(fr)) {
            ArrayList<Question> questions = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                int idx = line.lastIndexOf("\t");
                Question question = new Question(
                        line.substring(0, idx).replace('\t', '\n'), line.substring(idx + 1));
                questions.add(question);
            }
            return questions;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return questions;
    }

    public int getSize() {
        return questions.size();
    }
}
