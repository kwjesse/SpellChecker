package edu.isu.cs2235.structures.impl;

import edu.isu.cs2235.structures.Node;
import edu.isu.cs2235.structures.Tree;

import java.util.HashMap;

/**
 * A TrieTree Class
 *
 * @author Katherine Wilsdon
 */
public class TrieTree implements Tree {

    private TrieNode root;

    public TrieTree() {
        root = new TrieNode();
    }

    /**
     * @return the root node
     */
    @Override
    public Node root() {
        return this.root();
    }

    /**
     * Inserts the word into the tree under the root
     * Citation: https://www.programcreek.com/2014/05/leetcode-implement-trie-prefix-tree-java/
     *
     * @param word Word to be inserted into the tree.
     * @throws IllegalArgumentException if the the provided value is null
     */
    @Override
    public void insert(String word) throws IllegalArgumentException {
        if (word == null)
            throw new IllegalArgumentException("Cannot insert an empty word");

        HashMap<Character, TrieNode> children = root.getChildren();

        for (int i = 0; i < word.length(); ++i) {
            char chr = word.charAt(i);
            TrieNode node;

            if (children.containsKey(chr)){
                node = children.get(chr);
            } else {
                node = new TrieNode(chr);
                children.put(chr, node);
            }

            children = node.getChildren();

            if (i == word.length() - 1)
                node.setLastLetter(true);
        }

    }

    /**
     * Searches for a word in the tree
     * Citation: https://www.programcreek.com/2014/05/leetcode-implement-trie-prefix-tree-java/
     *
     * @param word Word to be searched for in the tree
     * @return If the word is in the trie
     */
    @Override
    public boolean search(String word) {
        TrieNode node = searchNode(word);
        if (node != null && node.isLastLetter())
            return true;
        else
            return false;
    }

    /**
     * Finds a node within the tree
     * Citation: https://www.programcreek.com/2014/05/leetcode-implement-trie-prefix-tree-java/
     *
     * @param str String to search for in the tree
     * @return The node if found or null if not found
     * @throws IllegalArgumentException if the the provided value is null
     */
    @Override
    public TrieNode searchNode(String str) throws IllegalArgumentException {
        if (str == null)
            throw new IllegalArgumentException("Cannot search for an empty word");
        HashMap<Character, TrieNode> children = root.getChildren();
        char[] charArr = str.toCharArray();
        TrieNode node = null;
        for (int i = 0; i < str.length(); ++i) {
            char chr = charArr[i];
            if (children.containsKey(chr)){
                node = children.get(chr);
                children = node.getChildren();
            } else {
                return null;
            }
        }
        return node;
    }

    /**
     * Finds all nodes that start with the provided prefix
     *
     * @param prefix the beginning of the word to search for
     * @return HashMap of characters and trie nodes that start with the given prefix
     * @throws IllegalArgumentException if the the provided value is null
     */
    @Override
    public  HashMap<Character, TrieNode> startsWith(String prefix) throws IllegalArgumentException {
        if (prefix == null)
            throw new IllegalArgumentException("Cannot search for an empty word");

        HashMap<Character, TrieNode> children = root.getChildren();

        TrieNode node = null;
        for (int i = 0; i < prefix.length(); ++i) {
            char chr = prefix.charAt(i);
            if (children.containsKey(chr)){
                node = children.get(chr);
                children = node.getChildren();
            } else {
                return null;
            }
        }
        return children;
    }
}
