package edu.isu.cs2235.structures;

import edu.isu.cs2235.structures.impl.TrieTree;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Edit distance class that is the interface for all spell checkers.
 *
 * @author Katherine Wilsdon
 */
public interface EditDistance {

    /**
     * Splits the word into left and right parts for each combination of letters
     *
     * @param word the misspelled word to be split
     * @return a list containing the left and right parts of a word incremented/decremented by one letter
     */
    public ArrayList<HashMap<String, String>> splitWord(String word);

    /**
     * All edits that are one edit distance away from word
     *
     * @param trie the trie tree to check for existing words
     * @param split a list containing the left and right parts of a word incremented/decremented by one letter
     * @return A list of known words that are one edit distance away from word
     */
    public ArrayList<String> editDistance1(TrieTree trie, ArrayList<HashMap<String, String>> split);

    /**
     * All edits that are two edit distances away from word
     *
     * @param trie the trie tree to check for existing words
     * @param split a list containing the left and right parts of a word incremented/decremented by one letter
     * @return A list of known words that are two edit distances away from word
     */
    public ArrayList<String> editDistance2(TrieTree trie, ArrayList<HashMap<String, String>> split);
}
