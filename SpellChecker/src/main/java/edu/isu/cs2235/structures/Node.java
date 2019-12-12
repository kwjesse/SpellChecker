package edu.isu.cs2235.structures;

import edu.isu.cs2235.structures.impl.TrieNode;

import java.util.HashMap;

/**
 * Node is the interface for all nodes for trees.
 *
 * @author Katherine Wilsdon
 */
public interface Node {

    /**
     * @return The element contained in this node.
     */
    char getElement();
    /**
     * @return the children of the node
     */
    HashMap<Character, TrieNode> getChildren();

    /**
     * @return whether the letter is the last letter
     */
    boolean isLastLetter();

    /**
     * Sets the new value of this node to the provided one. Throws an
     * IllegalArgumentException if the provided value is null.
     *
     * @param element New value to be contained in this node.
     */
    void setElement(char element) throws IllegalArgumentException;

    /**
     * Sets whether the letter is the last letter of the word
     *
     * @param lastLetter whether the letter is the last letter
     */
    void setLastLetter(boolean lastLetter);
}
