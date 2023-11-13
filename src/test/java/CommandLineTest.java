import base.DictionaryCommandline;

import java.util.Scanner;

public class CommandLineTest {
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
