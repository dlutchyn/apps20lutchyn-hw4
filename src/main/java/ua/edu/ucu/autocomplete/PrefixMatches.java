package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        String[] words;
        for (String string: strings) {
            words = string.split(" ");
            for (String word: words) {
                if (word.length() > 2) {
                    Tuple wordTuple =
                            new Tuple(word, word.length());
                    trie.add(wordTuple);
                }
            }
        }
        return trie.size();
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref.length() < 2) {
            return null;
        }
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() < 2) {
            return null;
        }
        Iterable<String> allWordIter = trie.wordsWithPrefix(pref);
        String[] allWordArr = new String[trie.size()];

        int index = 0;
        for (String word: allWordIter) {
            allWordArr[index] = word;
            index++;
        }
        Arrays.sort(allWordArr, (a, b)->Integer.compare(a.length(), b.length()));

        ArrayList<String> finalWordList = new ArrayList<>();
        int lenCount = 0;
        int curLen = 0;
        for (String word: allWordArr) {
            if (word.length() > curLen) {
                lenCount++;
                curLen = word.length();
            }
            if (lenCount > k) {
                break;
            }
            finalWordList.add(word);
        }
        return finalWordList;
    }

    public int size() {
        return trie.size();
    }
}
