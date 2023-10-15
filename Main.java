import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        DictionaryCommandline newCommandLine = new DictionaryCommandline();
        char c;
        do {
            System.out.println("*****Dictionary CommandLine*****");
            System.out.println("0: Exit");
            System.out.println("1: DictionaryBasic");
            System.out.println("2: DictionaryAdvanced");

            Scanner reader = new Scanner(System.in);
            c = reader.next().charAt(0);

            switch (c) {
                case '0':
                    System.out.println("Closing app ...");
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
        } while (c !='0');
    }
}