package src.base;

import java.util.Scanner;

public class DictionaryCommandline {

    private static Scanner scanner = new Scanner(System.in);

    public static void showAllWords(Dictionary dictionary) {
        dictionary.getWords().sort((word1, word2) -> word1.getWordTarget().compareTo(word2.getWordTarget()));

        System.out.println("No | English | Vietnamese");

        for (int i = 0; i < dictionary.getWords().size(); i++) {
            System.out.printf("%d | %s | %s\n", i + 1, dictionary.getWords().get(i).getWordTarget(), dictionary.getWords().get(i).getWordExplain());
        }
    }

    public static void dictionaryBasic(Dictionary dictionary) {
        DictionaryManagement.insertFromCommandLine(dictionary);

        showAllWords(dictionary);
    }
}