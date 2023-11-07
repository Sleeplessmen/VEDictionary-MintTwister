import java.io.*;import java.util.*;package base;public class DictionaryManagement extends Dictionary {    private static final String INPUTFILEPATH = "FirstverDictionary.txt";    private static final String OUTPUTFILEPATH = "OutputDictionaryFile.txt";    private static final String fileName = "anhviet109K.txt";    public void showAllWords() {        int counter = 1;        for (Word word : listWord) {            String wordTarget = word.getWord_target().trim();            String wordExplain = word.getWord_explain().trim();            System.out.println(counter + ". " + wordTarget);            System.out.println("- Meaning: " + wordExplain);            System.out.println();            counter++;        }    }    public void showOneWord(Word word) {        String wordTarget = word.getWord_target().trim();        String wordExplain = word.getWord_explain().trim();        System.out.println(wordTarget);        System.out.println("- Meaning: " + wordExplain);        System.out.println();    }    public void insertFromCommandLine() {        Scanner scanner = new Scanner(System.in);        int NumberOfWords = 0;        boolean validInput = false;        while (!validInput) {            System.out.print("Number of words: ");            try {                NumberOfWords = Integer.parseInt(scanner.nextLine());                validInput = true;            } catch (NumberFormatException e) {                System.out.println("Invalid input. Please enter an integer.");            }        }        for (int i = 0; i < NumberOfWords; i++) {            Word word = new Word();            System.out.println("Enter a new word: ");            word.setWord_target(scanner.nextLine());            System.out.println("Enter meanings of the word: ");            word.setWord_explain(scanner.nextLine());            listWord.add(word);        }    }    public void insertFromFile() {        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {            String line;            String wordTarget = "";            StringBuilder wordExplain = new StringBuilder();            String wordPronunciation = "";            while ((line = reader.readLine()) != null) {                line = line.trim();                if (line.startsWith("@")) {                    // Extract the word target                    wordTarget = line.substring(1).trim();                } else if (line.startsWith("*")) {                    // Extract the word explanation and pronunciation                    String lineContents = line.substring(1).trim();                    // Check for pronunciation enclosed in "/" characters                    int pronunciationStart = lineContents.indexOf('/');                    if (pronunciationStart != -1) {                        int pronunciationEnd = lineContents.lastIndexOf('/');                        if (pronunciationEnd > pronunciationStart) {                            wordExplain = new StringBuilder(lineContents.substring(0, pronunciationStart).trim());                            wordPronunciation = lineContents.substring(pronunciationStart + 1, pronunciationEnd).trim();                        } else {                            wordExplain = new StringBuilder(lineContents);                        }                    } else {                        wordExplain = new StringBuilder(lineContents);                    }                } else if (line.startsWith("-")) {                    // Append the word meaning to the word explanation                    wordExplain.append(" ").append(line.substring(1).trim());                } else if (!line.isEmpty()) {                    // Add the word to the dictionary list                    Word word = new Word(wordTarget, wordExplain.toString(), wordPronunciation);                    listWord.add(word);                    // Reset variables for the next word                    wordTarget = "";                    wordExplain = new StringBuilder();                    wordPronunciation = "";                }            }        } catch (IOException e) {            e.printStackTrace();        }    }    public void dictionaryLookup() {        System.out.print("enter the keyword: ");        Scanner scan = new Scanner(System.in);        String a = scan.nextLine();        listWord.stream().filter((i) ->                        (i.getWord_target().equals(a))).                forEachOrdered(this::showOneWord);    }    public void ExportWordToFile() {//        try {//            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTPUTFILEPATH)));//        }    }    public void insertWord() {        Scanner scan = new Scanner(System.in);        System.out.print("Enter the word to add: ");        String stringWord = scan.nextLine();        boolean check = false;        for(Word w : listWord) {            if(w.getWord_target().equalsIgnoreCase(stringWord)) {                System.out.println("The word ís already in the ");                check = true;                break;            }        }        if(!check) {            System.out.print("Enter the meaning of the word: ");            String ex = scan.nextLine();            System.out.print("Enter the pronunciation of the word: ");            String pr = scan.nextLine();            listWord.add(new Word(stringWord, ex,pr));            System.out.println("Insert word successfully.");        }    }    public void removeWord() {        Scanner scan = new Scanner(System.in);        System.out.print("Enter the word to remove: ");        String target = scan.nextLine();        boolean check = false;        for(Word w : listWord) {            if(w.getWord_target().equalsIgnoreCase(target)) {                listWord.remove(w);                check = true; break;            }        }        if(check) {            System.out.println("Remove word successfully.");        }        else {            System.out.println("The word does not exist in the ");        }    }    public void editWord() {        System.out.print("Enter the word to edit: ");        Scanner scan = new Scanner(System.in);        String word = scan.nextLine();        boolean check = false;        for(Word w : listWord) {            if(w.getWord_target().equalsIgnoreCase(word)) {                System.out.print("Enter the alternative word: ");                String target = scan.nextLine();                w.setWord_target(target);                System.out.print("Enter the meaning of the alternative word: ");                String explain = scan.nextLine();                w.setWord_explain(explain);                check = true;                break;            }        }        if(!check) {            System.out.println("The word does not exist in the ");        }    }    public void dictionarySearcher() {        System.out.print("Enter the keyword to search: ");        Scanner scan = new Scanner(System.in);        String s = scan.nextLine();        listWord.forEach((i) -> {            int index = i.getWord_target().indexOf(s);            if(index == 0) {                System.out.println(i.getWord_target());            }        });    }}