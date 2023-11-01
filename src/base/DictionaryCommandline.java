import java.io.IOException;
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
                myDictionaryMng.insertFromCommandLine();
                break;
            case '2':
                myDictionaryMng.showAllWords();
                break;
            default:
                System.out.println("Invalid Query!!!Please type in valid query(number from 0 to 2)");
                break;
        }
        dictionaryBasic();
    }

    public void dictionaryAdvanced() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n*****Dictionary Advanced*****!");
        System.out.println("[0] Exit");
        System.out.println("[1] Add");
        System.out.println("[2] Remove");
        System.out.println("[3] Update");
        System.out.println("[4] Display");
        System.out.println("[5] Lookup");
        System.out.println("[6] Search");
        System.out.println("[7] Game");
        System.out.println("[8] Import word from file");
        System.out.println("[9] Export to file");
        System.out.print("Your action: ");

        char query = scanner.next().charAt(0);
        switch (query) {
            case '0':
                System.out.println("Closing Dictionary Advanced Version...");
                return;
            case '1':
                myDictionaryMng.insertWord();
                break;
            case '2':
                myDictionaryMng.removeWord();
                break;
            case '3':
                myDictionaryMng.editWord();
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
                break;
            case '8':
                myDictionaryMng.insertFromFile();
                break;
            case '9':
                myDictionaryMng.ExportWordToFile();
                break;
            default:
                System.out.println("Invalid Query!!!Please type in valid query(number from 0 to 9)");
                break;
        }
        dictionaryAdvanced();
    }

    public static void main(String[] args) throws IOException {
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
                    break;
            }
        } while (query != '0');
    }
}
