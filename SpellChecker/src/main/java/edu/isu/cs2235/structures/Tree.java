package edu.isu.cs2235.structures;

import edu.isu.cs2235.structures.impl.TrieNode;

import java.util.HashMap;

/**
 * Tree is the interface for all trees.
 *
 * @author Katherine Wilsdon
 */
public interface Tree {

    /**
     * @return The root node of this tree.
     */
    Node root();

    /**
     * Inserts the word into the tree under the root
     *
     * @param word Word to be inserted into the tree.
     * @throws IllegalArgumentException if the the provided value is null
     */
    void insert(String word) throws IllegalArgumentException;

    /**
     * Searches for a word in the tree
     *
     * @param word Word to be searched for in the tree
     * @return If the word is in the trie
     */
    boolean search(String word);

    /**
     * Finds a node within the tree
     *
     * @param str String to search for in the tree
     * @return The node if found or null if not found
     * @throws IllegalArgumentException if the the provided value is null
     */
    TrieNode searchNode(String str) throws IllegalArgumentException;

    /**
     * Finds all nodes that start with the provided prefix
     * @param prefix the beginning of the word to search for
     * @return List of trie nodes that start with the given prefix
     * @throws IllegalArgumentException if the the provided value is null
     */
    HashMap<Character, TrieNode> startsWith(String prefix) throws IllegalArgumentException;
}
