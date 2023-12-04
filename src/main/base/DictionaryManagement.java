package base;
import java.io.*;
import java.util.*;

public class DictionaryManagement extends Dictionary {
    private static final String OUTPUTFILEPATH = "src/resources/OutputDictionaryFile.txt";
    private static final String fileName = "src/resources/anhviet109K.txt";


    public void showAllWords() {
        int counter = 1;
        System.out.printf("%-5s| %-15s| %-15s| %-15s%n", "No", "English", "Pronunciation", "Vietnamese");

        for (Word word : listWord) {
            System.out.printf("%-5s| %-15s| %-15s| %-15s%n", counter, word.getWordTarget(), word.getWordPronunciation(), word.getWordExplain());
            counter++;
        }
    }

    public void showOneWord(Word word) {
        String wordTarget = word.getWordTarget().trim();
        String wordExplain = word.getWordExplain().trim();
        System.out.println(wordTarget);
        System.out.println("- Meaning: " + wordExplain);
        System.out.println();
    }


    public void insertFromCommandLine() {
        Scanner scanner = new Scanner(System.in);
        int NumberOfWords = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("Number of words: ");
            try {
                NumberOfWords = Integer.parseInt(scanner.nextLine());
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }

        for (int i = 0; i < NumberOfWords; i++) {
            Word word = new Word();
            System.out.println("Enter a new word: ");
            word.setWordTarget(scanner.nextLine());
            System.out.println("Enter meanings of the word: ");
            word.setWordExplain(scanner.nextLine());
            listWord.add(word);
        }
    }


    public void insertWordFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String wordTarget = "";
            StringBuilder wordExplain = new StringBuilder();
            String wordPronunciation = "";
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("@")) {
                    wordTarget = line.substring(1).trim();
                    int pronunciationStart = wordTarget.indexOf('/');
                    if (pronunciationStart != -1) {
                        int pronunciationEnd = wordTarget.lastIndexOf('/');
                        if (pronunciationEnd > pronunciationStart) {
                            wordPronunciation = wordTarget.substring(pronunciationStart + 1, pronunciationEnd).trim();
                            wordTarget = wordTarget.substring(0, pronunciationStart).trim();
                        }
                    }
                } else if (line.startsWith("*")) {
                    String lineContents = line.substring(1).trim();
                    wordExplain = new StringBuilder(lineContents);
                } else if (line.startsWith("-")) {
                    wordExplain.append(" ").append(line.substring(1).trim());
                } else if (!line.isEmpty()) {
                    Word word = new Word(wordTarget, wordExplain.toString(), wordPronunciation);
                    listWord.add(word);
                    wordTarget = "";
                    wordExplain = new StringBuilder();
                    wordPronunciation = "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void dictionaryLookup() {
        Scanner scanner = new Scanner(System.in);
        int query;
        Collections.sort(listWord); // Sort the list once after loading all words
        do {
            System.out.print("Enter the word: ");
            String wordTarget = scanner.nextLine().replaceAll("\\s", "");
            int idx = Collections.binarySearch(listWord, new Word(wordTarget, null, null));
            if (idx >= 0) {
                System.out.println(listWord.get(idx).getWordPronunciation());
                System.out.println(listWord.get(idx).getWordExplain());
            } else {
                System.out.println("The word does not exist in the dictionary.");
            }

            System.out.print("Do you want to continue to look up? Yes(1) No(2): ");
            try {
                query = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter 1 for Yes or 2 for No.");
                query = 2; // Default to No if input is invalid
            }
        } while (query == 1);
    }
        public static void exportToFile() {
            File file = new File(OUTPUTFILEPATH);
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
                String format = "%-15s %-20s %-15s%n";
                for (Word word : listWord) {
                    bufferedWriter.write(String.format(format, word.getWordTarget(), word.getWordPronunciation(), word.getWordExplain()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    private static String formatMeanings(String wordExplain) {
        String[] meanings = wordExplain.split("\\n");
        StringBuilder formattedMeanings = new StringBuilder();
        for (String meaning : meanings) {
            formattedMeanings.append("-").append(meaning.trim()).append("\n");
        }
        return formattedMeanings.toString();
    }

    public void addWord() {
        Scanner scanner = new Scanner(System.in);
        int numberOfWords = 0;
        int option = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Number of words: ");
            String input = scanner.nextLine().trim();
            if (input.matches("[0-9]+")) {
                numberOfWords = Integer.parseInt(input);
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter an positive integer.");
                System.out.println("Continue to add word? Yes(y)/No(n)");
                char query = scanner.nextLine().charAt(0);
                if (query == 'n') {
                    return;
                }
            }
        }

        for (int i = 0; i < numberOfWords; i++) {
            while (true) {
                System.out.println("Enter a new word: ");
                String wordTarget = scanner.nextLine().trim().toLowerCase();
                System.out.println("Enter pronunciation: ");
                String wordPronunciation = scanner.nextLine().trim().toLowerCase();
                System.out.println("Enter meanings of the word: ");
                String wordExplain = scanner.nextLine().trim().toLowerCase();

                if (!wordTarget.isEmpty() && !wordPronunciation.isEmpty() && !wordExplain.isEmpty()) {
                    listWord.add(new Word(wordTarget, wordExplain, wordPronunciation));
                    System.out.println("Add word successfully to the dictionary.");
                    Collections.sort(listWord);
                    break;
                } else {
                    System.err.println("Word, pronunciation, or explanation cannot be empty.");
                    System.out.print("Continue? Yes(1) No(2): ");
                    option = Integer.parseInt(scanner.nextLine().trim());
                    if (option != 1) {
                        return;
                    }
                }
            }
        }
    }

    public int binarySearchWordTarget(int start, int end, String wordTarget) {
        if (start > end) {
            return -1;
        }
        int mid = start + ((end - start) / 2);
        if (mid == 0 && listWord.isEmpty()) {
            return -1;
        }
        int comp = wordTarget.compareTo(listWord.get(mid).getWordTarget());
        if (comp == 0) {
            return 0;
        } else if (comp < 0) {
            // Return the result of the recursive call
            return binarySearchWordTarget(start, mid - 1, wordTarget);
        } else {
            // Return the result of the recursive call
            return binarySearchWordTarget(mid + 1, end, wordTarget);
        }
    }

    public void editWord() {
        System.out.print("Enter the word to edit: ");
        Scanner scan = new Scanner(System.in);
        String word = scan.nextLine();

        boolean check = false;
        for(Word w : listWord) {
            if(w.getWordTarget().equalsIgnoreCase(word)) {
                System.out.print("Enter the alternative word: ");
                String target = scan.nextLine();
                w.setWordTarget(target);

                System.out.print("Enter the meaning of the alternative word: ");
                String explain = scan.nextLine();
                w.setWordExplain(explain);

                check = true;
                break;
            }
        }

        if(!check) {
            System.out.println("The word does not exist in the ");
        }
    }

    public void dictionarySearcher() {
        System.out.print("Enter the keyword to search: ");
        Scanner scanner = new Scanner(System.in);
        String keyword = scanner.nextLine().trim();

        List<String> suggestions = new ArrayList<>();

        for (Word word : listWord) {
            String wordTarget = word.getWordTarget();
            if (wordTarget.toLowerCase().startsWith(keyword.toLowerCase())) {
                suggestions.add(wordTarget);
            }
        }

        for (String suggestion : suggestions) {
            System.out.println(suggestion);
        }
    }

    public void removeWord() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the word to remove: ");
        String target = scanner.nextLine().trim().toLowerCase();

        int idx = Collections.binarySearch(listWord, new Word(target, null));
        if (idx >= 0) {
            listWord.remove(listWord.get(idx));
            System.out.println("Remove word successfully.");
        } else {
            System.out.println("The word does not exist in the dictionary. ");
        }
    }

    /** done. */
    public void modifyWord() {
        System.out.println("Enter the word to edit: ");
        Scanner scanner = new Scanner(System.in);
        String target = scanner.nextLine().trim().toLowerCase();
        String explain = scanner.nextLine().trim().toLowerCase();
        String pronunciation = scanner.nextLine().trim().toLowerCase();

        int idx = Collections.binarySearch(listWord, new Word(target, null));
        if (idx >= 0) {
            listWord.get(idx).setWordExplain(explain);
            listWord.get(idx).setWordPronunciation(pronunciation);
            System.out.println("The word has been removed successfully.");
        } else {
            System.out.println("The word does not exist in the dictionary.");
        }
    }

    public void mcq() {
        Scanner scanner = new Scanner(System.in);
        int query;
        int idx;
        Random random = new Random();
        QuizGame qg = new QuizGame();
        qg.readquestionsfromFile();

        // Set of valid options
        Set<String> validOptions = Set.of("a", "b", "c", "d");

        System.out.println(" ******RANDOM QUIZ GAME****** ");
        System.out.println("  (Multiple-choice question)  ");
        System.out.println("1.Start");
        System.out.println("2.Return");
        System.out.print("Your choice:");
        query = Integer.parseInt(scanner.nextLine());
        if (query == 2) {
            return;
        }

        do {
            idx = random.nextInt(qg.getSize());
            QuizGame.Question currentQuestion = qg.getQuestions().get(idx);
            System.out.println(currentQuestion.prompt());
            System.out.println("Your choice (a, b, c, or d):");
            String userAnswer = scanner.nextLine().toLowerCase();

            // Check if the userAnswer is a valid option
            if (validOptions.contains(userAnswer)) {
                String correctAnswer = currentQuestion.answer().toLowerCase();

                if (userAnswer.equals(correctAnswer)) {
                    System.out.println("Correct answer.");
                } else {
                    System.out.println("Wrong answer. The correct answer is: " + correctAnswer);
                }
            } else {
                System.out.println("Invalid choice. Please enter a, b, c, or d.");
            }

            System.out.print("Continue? Yes(1) No(2):");
            query = Integer.parseInt(scanner.nextLine());
        } while (query == 1);
    }

}


