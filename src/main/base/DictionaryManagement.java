package base;
import java.io.*;
import java.util.*;

public class DictionaryManagement extends Dictionary {
    private static final String INPUTFILEPATH = "src/resources/FirstverDictionary.txt";
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
                    // Extract the word target
                    wordTarget = line.substring(1).trim();
                } else if (line.startsWith("*")) {
                    // Extract the word explanation and pronunciation
                    String lineContents = line.substring(1).trim();

                    // Check for pronunciation enclosed in "/" characters
                    int pronunciationStart = lineContents.indexOf('/');
                    if (pronunciationStart != -1) {
                        int pronunciationEnd = lineContents.lastIndexOf('/');
                        if (pronunciationEnd > pronunciationStart) {
                            wordExplain = new StringBuilder(lineContents.substring(0, pronunciationStart).trim());
                            wordPronunciation = lineContents.substring(pronunciationStart + 1, pronunciationEnd).trim();
                        } else {
                            wordExplain = new StringBuilder(lineContents);
                        }
                    } else {
                        wordExplain = new StringBuilder(lineContents);
                    }
                } else if (line.startsWith("-")) {
                    // Append the word meaning to the word explanation
                    wordExplain.append(" ").append(line.substring(1).trim());
                } else if (!line.isEmpty()) {
                    // Add the word to the dictionary list
                    Word word = new Word(wordTarget, wordExplain.toString(), wordPronunciation);
                    listWord.add(word);

                    // Reset variables for the next word
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
        do {
            System.out.print("enter the word:");
            String wordTarget = scanner.nextLine();
            int idx = Collections.binarySearch(listWord, new Word(wordTarget, null));
            if (idx >= 0) {
                System.out.println(listWord.get(idx).getWordPronunciation());
                System.out.println(listWord.get(idx).getWordExplain());
            } else {
                System.out.println("The word does not exist in the dictionary.");
            }
            System.out.print("Do you want to continue to look up? Yes(1) No(2):");
            query = Integer.parseInt(scanner.nextLine());
        } while (query == 1);
    }

    public void exportWordToFile() {
        try (FileOutputStream fos = new FileOutputStream(OUTPUTFILEPATH);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
            int number = 1;

            for (Word word : listWord) {
                // Format: Number. WordTarget WordPronunciation
                bw.write(number + ". " + word.getWordTarget() + " " + word.getWordPronunciation());
                bw.newLine();

                // WordExplain
                String[] explanations = word.getWordExplain().split("\n");
                for (String explanation : explanations) {
                    // Add bullet point for each explanation line
                    bw.write("- " + explanation.trim());
                    bw.newLine();
                }

                // Separate entries with an empty line
                bw.newLine();

                number++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addWord() {
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        int numberOfWords = 0;
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
            String correctAnswer = currentQuestion.answer().toLowerCase();

            if (userAnswer.equals(correctAnswer)) {
                System.out.println("Correct answer.");
            } else {
                System.out.println("Wrong answer. The correct answer is: " + correctAnswer);
            }

            System.out.print("Continue? Yes(1) No(2):");
            query = Integer.parseInt(scanner.nextLine());
        } while (query == 1);
    }
}


