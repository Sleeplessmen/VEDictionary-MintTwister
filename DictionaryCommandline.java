import java.io.IOException;
import java.util.Scanner;

public class DictionaryCommandline {
    DictionaryManagement myDictionaryMng = new DictionaryManagement();


    public void dictionaryBasic() {
        Scanner reader = new Scanner(System.in);
        System.out.println("*****Dictionary Basic*****\n");
        System.out.println("0: Exit\n");
        System.out.println("1: Add words from commandline\n");
        System.out.println("2: Show all words\n");

        char c = reader.next().charAt(0);
        switch (c) {
            case '0':
                System.out.println("Closing app ...");
                return;
            case '1':
                myDictionaryMng.insertFromCommandLine();
                break;
            case '2':
                myDictionaryMng.showAllWords();
                break;
            default:
                break;
        }
        dictionaryBasic();
    }

    public void dictionaryAdvanced() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("*****Dictionary Advanced*****!\n");
        System.out.println("[0] Exit\n");
        System.out.println("[1] Add\n");
        System.out.println("[2] Remove\n");
        System.out.println("[3] Update\n");
        System.out.println("[4] Display\n");
        System.out.println("[5] Lookup\n");
        System.out.println("[6] Search\n");
        System.out.println("[7] Game\n");
        System.out.println("[8] Import from file\n");
        System.out.println("[9] Export to file\n");
        System.out.println("Your action:");

        char c = scan.next().charAt(0);
        switch (c) {
            case '0':
                System.out.println("Closing app ...");
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
                myDictionaryMng.dictionaryExportToFile();
                break;
            default:
                dictionaryAdvanced();
                break;
        }
        System.out.println("\n");
        dictionaryAdvanced();
    }


}
