import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DictionaryManagement {

    public static void insertFromCommandLine(Dictionary dictionary) {
        System.out.println("Nhập số lượng từ vựng: ");
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.println("Nhập từ vựng thứ " + (i + 1) + ": ");
            String wordTarget = scanner.nextLine();

            System.out.println("Nhập giải thích của từ vựng: ");
            String wordExplain = scanner.nextLine();

            dictionary.addWord(new Word(wordTarget, wordExplain));
        }
    }

    public static void insertFromFile(Dictionary dictionary) throws FileNotFoundException, IOException {
        // Mở tệp dictionaries.txt
        File file = new File("dtb.txt");
        Scanner scanner = new Scanner(file);

        // Lặp qua từng dòng trong tệp
        while (scanner.hasNextLine()) {
            // Lấy từ tiếng anh và giải thích tiếng việt từ dòng hiện tại
            String line = scanner.nextLine();
            String[] words = line.split("\t");

            // Tạo một từ vựng mới
            Word word = new Word(words[0], words[1]);

            // Thêm từ vựng vào từ điển
            dictionary.addWord(word);
        }

        // Đóng tệp
        scanner.close();
    }
}