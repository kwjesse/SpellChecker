package edu.isu.cs2235.structures.impl;

import edu.isu.cs2235.structures.Node;

import java.util.HashMap;

/**
 * A TrieTree Node Class
 *
 * @author Katherine Wilsdon
 */
public class TrieNode implements Node {
    private char element;
    private HashMap<Character, TrieNode> children = new HashMap<>();
    private boolean isLastLetter = false;

    /**
     * Constructs a trie node that contains an element
     * @param element the letter
     */
    public TrieNode(char element) {
        this.element = element;
    }

    /**
     * Constructs the root node
     */
    public TrieNode() {
    }

    /**
     * @return The letter contained in this node.
     */
    @Override
    public char getElement() {
        return element;
    }


    /**
     * @return the children of the node
     */
    public HashMap<Character, TrieNode> getChildren() {
        return children;
    }

    /**
     * @return whether the letter is the last letter
     */
    public boolean isLastLetter() {
        return isLastLetter;
    }

    /**
     * Sets the new value of this node to the provided one. Throws an
     * IllegalArgumentException if the provided value is null.
     *
     * @param element New value to be contained in this node.
     */
    @Override
    public void setElement(char element) throws IllegalArgumentException {
        this.element = element;
    }

    /**
     * Sets whether the letter is the last letter of the word
     *
     * @param lastLetter whether the letter is the last letter
     */
    public void setLastLetter(boolean lastLetter) {
        isLastLetter = lastLetter;
    }
}
