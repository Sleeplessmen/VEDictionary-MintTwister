package base;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Trie {
    private static final ArrayList<String> words = new ArrayList<>();
    private static final TrieNode root = new TrieNode();

    public static class TrieNode {
        Map<Character, TrieNode> children = new TreeMap<>();
        boolean isEnd;
    }

    public static void addWord(String wordTarget) {
        TrieNode current = root;
        for (int i = 0; i < wordTarget.length(); i++) {
            char temp = wordTarget.charAt(i);

            if (current.children.get(temp) == null) {
                current.children.put(temp, new TrieNode());
            }

            current = current.children.get(temp);
        }
        current.isEnd = true;
    }

    private static void getWordsStartWith(TrieNode current, String wordTarget) {
        // check if end of word true -> add
        if (current.isEnd) {
            words.add(wordTarget);
        }
        for (char temp : current.children.keySet()) {
            if (current.children.get(temp) != null) {
                getWordsStartWith(current.children.get(temp), wordTarget + temp);
            }
        }
    }

    public static ArrayList<String> searchWordStartsWith(String prefix) {
        if (prefix.isEmpty()) {
            return new ArrayList<>();
        }
        words.clear();
        TrieNode current = root;

        for (int i = 0; i < prefix.length(); i++) {
            char temp = prefix.charAt(i);

            if (current.children.get(temp) == null) {
                return words;
            }

            current = current.children.get(temp);
        }
        getWordsStartWith(current, prefix);
        return words;
    }

    public static void removeWord(String wordTarget) {
        TrieNode current = root;
        for (int i = 0; i < wordTarget.length(); i++) {
            char temp = wordTarget.charAt(i);
            if (current.children.get(temp) == null) {
                System.out.println("The word is not available in the dictionary");
                return;
            }
            current = current.children.get(temp);
        }

        if (!current.isEnd) {
            System.out.println("The word is not available in the dictionary");
            return;
        }
        current.isEnd = false;
    }

}
