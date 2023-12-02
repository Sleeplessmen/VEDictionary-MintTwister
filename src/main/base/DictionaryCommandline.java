package base;

import java.util.Scanner;
public class DictionaryCommandline {
    DictionaryManagement myDictionaryMng = new DictionaryManagement();

    public void dictionaryBasic() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*****Dictionary Basic*****");
        System.out.println("0: Exit");
        System.out.println("1: Add words from commandline");
        System.out.println("2: Show all words");
        System.out.print("Your action: ");

        char query = scanner.next().charAt(0);
        switch (query) {
            case '0':
                System.out.println("Closing Dictionary Basic Version...");
                return;
            case '1':
                myDictionaryMng.addWord();
                break;
            case '2':
                myDictionaryMng.showAllWords();
                break;
            default:
                System.out.println("Action not supported.");
                break;
        }
        dictionaryBasic();
    }

    public void dictionaryAdvanced() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*****Dictionary Advanced*****!");
        System.out.println("[0] Exit");
        System.out.println("[1] Add");
        System.out.println("[2] Remove");
        System.out.println("[3] Modify");
        System.out.println("[4] Display");
        System.out.println("[5] Lookup");
        System.out.println("[6] Search");
        System.out.println("[7] Multiple-Choice Game");
        System.out.println("[8] Import dictionary from file");
        System.out.println("[9] Export dictionary to file");
        System.out.print("Your action: ");

        char query = scanner.next().charAt(0);
        switch (query) {
            case '0':
                System.out.println("Closing Dictionary Advanced Version...");
                return;
            case '1':
                myDictionaryMng.addWord();
                break;
            case '2':
                myDictionaryMng.removeWord();
                break;
            case '3':
                myDictionaryMng.modifyWord();
                break;
            case '4':
                myDictionaryMng.showAllWords();
                break;
            case '5':
                myDictionaryMng.dictionaryLookup();
                break;
            case '6':
                myDictionaryMng.dictionarySearcher();
                break;
            case '7':
                myDictionaryMng.mcq();
                break;
            case '8':
                myDictionaryMng.insertWordFromFile();
                break;
            case '9':
                myDictionaryMng.exportToFile();
                break;
            default:
                System.out.println("Action not supported.");
                break;
        }
        dictionaryAdvanced();
    }

    public static void main(String[] args) {
        DictionaryCommandline newCommandLine = new DictionaryCommandline();
        char query;
        do {
            System.out.println("*****Dictionary CommandLine*****");
            System.out.println("0: Exit");
            System.out.println("1: Dictionary Basic");
            System.out.println("2: Dictionary Advanced");
            System.out.print("Your action: ");
            Scanner reader = new Scanner(System.in);
            query = reader.next().charAt(0);

            switch (query) {
                case '0':
                    System.out.println("Closing Dictionary CommandLine ...");
                    break;
                case '1':
                    newCommandLine.dictionaryBasic();
                    break;
                case '2':
                    newCommandLine.dictionaryAdvanced();
                    break;
                default:
                    System.out.println("Action not supported.");
                    break;
            }
        } while (query != '0');
    }
}
