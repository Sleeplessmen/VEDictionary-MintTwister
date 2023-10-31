import java.io.BufferedOutputStream;import java.io.File;import java.io.FileNotFoundException;import java.io.FileOutputStream;import java.io.IOException;import java.util.Scanner;public class DictionaryManagement {    /**     * The method use show all the word in dictionary     */    public void showAllWords() {        int counter = 1;        System.out.println("No\t| English\t | Vietnamese");        for(Word w : Dictionary.listWord) {            System.out.println( (counter++) + "\t| " + w.getWord_target() + "\t | " + w.getWord_explain());        }    }    /**     * This method use insert list word from command line     * No parameter and return void     */    public void insertFromCommandLine() {        Scanner scan = new Scanner(System.in);        System.out.println("Number of words:");        int NumberOfWords = Integer.parseInt(scan.nextLine());        for (int i = 1; i <= NumberOfWords; i++) {            Word w = new Word();            System.out.println("Enter the new word:");            w.setWord_target(scan.nextLine());            System.out.println("Enter the meaning of word:");            w.setWord_explain(scan.nextLine());            Dictionary.listWord.add(w);        }    }    /**     * This method use insert list Word from my file text     * No parameter and return void     */    public void insertFromFile() throws FileNotFoundException {        Scanner scan = new Scanner(new File("SimpleDictionary.txt"));        while(scan.hasNext()) {            String line = scan.nextLine();            Scanner s = new Scanner(line).useDelimiter("s* \ts*");            Word w = new Word();            w.setWord_target(s.next());            w.setWord_explain(s.next());            Dictionary.listWord.add(w);        }    }    public void dictionaryLookup() {        System.out.println("enter the keyword:");        Scanner scan = new Scanner(System.in);        String a = scan.nextLine();        Dictionary.listWord.stream().filter((i) ->                        (i.getWord_target().equals(a))).                forEachOrdered( (i) -> System.out.println(i.getWord_explain()));    }    public void dictionaryExportToFile() throws IOException {        FileOutputStream fout = new FileOutputStream("SimpleDictionary.txt");        try (BufferedOutputStream bout = new BufferedOutputStream(fout)) {            for(Word w : Dictionary.listWord) {                String line = w.getWord_target() + "\t" + w.getWord_explain();                bout.write(line.getBytes());                bout.write(System.lineSeparator().getBytes());            }        }    }    public void insertWord() {        Scanner scan = new Scanner(System.in);        System.out.println("Enter the word to add:");        String stringWord = scan.nextLine();        boolean check = false;        for(Word w : Dictionary.listWord) {            if(w.getWord_target().equalsIgnoreCase(stringWord)) {                System.out.println("The word ís already in the dictionary.");                check = true;                break;            }        }        if(!check) {            System.out.println("Enter the meaning of the word:");            String ex = scan.nextLine();            Dictionary.listWord.add(new Word(stringWord, ex));            System.out.println("Insert word successfully.");        }    }    public void removeWord() {        Scanner scan = new Scanner(System.in);        System.out.println("Enter the word to remove:");        String target = scan.nextLine();        boolean check = false;        for(Word w : Dictionary.listWord) {            if(w.getWord_target().equalsIgnoreCase(target)) {                Dictionary.listWord.remove(w);                check = true; break;            }        }        if(check) {            System.out.println("Remove word successfully.");        }        else {            System.out.println("The word does not exist in the dictionary.");        }    }    public void editWord() {        System.out.println("Enter the word to edit:");        Scanner scan = new Scanner(System.in);        String word = scan.nextLine();        boolean check = false;        for(Word w : Dictionary.listWord) {            if(w.getWord_target().equalsIgnoreCase(word)) {                System.out.println("Enter the alternative word:");                String target = scan.nextLine();                w.setWord_target(target);                System.out.println("Enter the meaning of the alternative word:");                String explain = scan.nextLine();                w.setWord_explain(explain);                check = true;                break;            }        }        if(!check) {            System.out.println("The word does not exist in the dictionary.");        }    }    public void dictionarySearcher() {        System.out.println("Enter the keyword to search:");        Scanner scan = new Scanner(System.in);        String s = scan.nextLine();        Dictionary.listWord.forEach((i) -> {            int index = i.getWord_target().indexOf(s);            if(index == 0) {                System.out.println(i.getWord_target() + "\n");            }        });    }}