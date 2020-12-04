package ua.edu.ucu.tries;

import java.util.ArrayList;
import ua.edu.ucu.immutable.Queue;

public class RWayTrie implements Trie {
//    I implemented this class by watching this video -
//    https://www.coursera.org/lecture/algorithms-part2/r-way-tries-CPVdr
//    and reading this article -
//    https://www.cs.princeton.edu/courses/archive/spr04/cos226/lectures/trie.4up.pdf

    private static final int R = 26;
    private static final int ASCII_START = 97;
    private Node root = new Node();
    private int trieSize = 0;

    public static class Node {
        private Object value;
        private Node[] next = new Node[R];
    }

    @Override
    public void add(Tuple t) {
        root = add(root, t.term, t.weight, 0);
        trieSize++;
    }

    private static Node add(Node parent, String word,
                            int value, int letterIndex) {
        if (parent == null) {
            parent = new Node();
        }
        if (letterIndex == word.length()) {
            parent.value = value;
            return parent;
        }
        char letter = word.charAt(letterIndex);
        parent.next[letter - ASCII_START] =
                add(parent.next[letter - ASCII_START],
                        word, value, letterIndex + 1);
        return parent;
    }

    @Override
    public boolean contains(String word) {
        return get(word) != null;
    }

    public Object get(String word) {
        Node getNode = get(root, word, 0);
        if (getNode == null) {
            return null;
        }
        return getNode.value;
    }

    public static Node get(Node parent, String word,
                           int letterIndex) {
        if (parent == null) {
            return null;
        }
        if (letterIndex == word.length()) {
            return parent;
        }
        char letter = word.charAt(letterIndex);
        return get(parent.next[letter - ASCII_START],
                        word, letterIndex + 1);
    }

    @Override
    public boolean delete(String word) {
        if (contains(word)) {
            delete(root, word, 0);
            trieSize--;
            return true;
        }
        return false;
    }

    public static Node delete(Node parent, String word, int letterIndex) {
        if (parent == null) {
            return null;
        }
        if (letterIndex == word.length()) {
            parent.value = null;
        } else {
            char letter = word.charAt(letterIndex);
            parent.next[letter - ASCII_START] =
                    delete(parent.next[letter - ASCII_START],
                            word, letterIndex + 1);
        }
        // check if we can delete Node (if it has non null value or
        // if it has at least one non null child we do not delete it)
        if (parent.value != null) {
            return parent;
        }
        for (Node child: parent.next) {
            if (child != null) {
                return parent;
            }
        }
        return null;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        ArrayList<String> wordList = new ArrayList<>();
        Queue q = new Queue();
        Node start = get(root, s, 0);
        BFS(start, s, q);
        while (!q.isEmpty()) {
            wordList.add((String) q.dequeue());
        }
    return wordList;
    }

    public static void BFS(Node parent, String prefix, Queue q) {
        if (parent == null) {
            return;
        }
        if (parent.value != null) {
            q.enqueue(prefix);
        }
        for (int i = 0; i < R; i++) {
            if (parent.next[i] != null) {
                String newPrefix = prefix +
                        (char) (i + ASCII_START);
                BFS(parent.next[i], newPrefix, q);
            }
        }
    }

    @Override
    public int size() {
        return trieSize;
    }

}
