package edu.isu.cs2235.structures.impl;

import edu.isu.cs2235.structures.EditDistance;

import java.util.ArrayList;
import java.util.HashMap;
import java.lang.String;

/**
 * A Spell Checker Class
 *
 * @author Katherine Wilsdon
 */
public class SpellChecker implements EditDistance {
    private String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public SpellChecker() {
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    public String getLetters() {
        return letters;
    }

    /**
     * Splits the word into left and right parts for each combination of letters
     * Citation: http://norvig.com/spell-correct.html splits = [(word[:i], word[i:]) for i in range(len(word) + 1)]
     *
     * @param word the misspelled word to be split
     * @return a list containing the left and right parts of a word incremented/decremented by one letter
     */
    public ArrayList<HashMap<String, String>> splitWord(String word){
        ArrayList<HashMap<String, String>> splitWordList = new ArrayList<>();
        for (int i = 0; i < word.length(); ++i) {
            String left = word.substring(0, i);
            String right = word.substring(i);
            HashMap<String, String> splitWord = new HashMap<String, String>();
            splitWord.put(left, right);
            splitWordList.add(splitWord);
        }
        return splitWordList;
    }

    /**
     * All edits that are one edit distance away from word
     *
     * @param trie the trie tree to check for existing words
     * @param split a list containing the left and right parts of a word incremented/decremented by one letter
     * @return A list of known words that are one edit distance away from word
     */
    public ArrayList<String> editDistance1(TrieTree trie, ArrayList<HashMap<String, String>> split) {

        ArrayList<String> existingWords;

        ArrayList<String> left = splitLeft(split);
        ArrayList<String> right = splitRight(split);
        ArrayList<String> possibleDeletionWords = deletion(left, right);
        ArrayList<String> possibleTransposeWords = transpose(left, right);
        ArrayList<String> possibleReplacementWords = replacement(left, right);
        ArrayList<String> possibleInsertionWords = insertion(left, right);

        existingWords = knownWords(trie, possibleDeletionWords, possibleTransposeWords, possibleReplacementWords, possibleInsertionWords);

        return existingWords;
    }

    /**
     * All edits that are two edit distances away from word
     *
     * @param trie the trie tree to check for existing words
     * @param split a list containing the left and right parts of a word incremented/decremented by one letter
     * @return A list of known words that are two edit distances away from word
     */
    public ArrayList<String> editDistance2(TrieTree trie, ArrayList<HashMap<String, String>> split) {

        ArrayList<String> existingWords = new ArrayList<>();

        ArrayList<String> left = splitLeft(split);
        ArrayList<String> right = splitRight(split);

        // One edit distance from word
        ArrayList<String> possibleDeletionWords = deletion(left, right);
        ArrayList<String> possibleTransposeWords = transpose(left, right);
        ArrayList<String> possibleReplacementWords = replacement(left, right);
        ArrayList<String> possibleInsertionWords = insertion(left, right);

        // Add all known words two edit distances from word to existingWords list
        existingWords.addAll(secondEdit(trie, possibleDeletionWords));
        existingWords.addAll(secondEdit(trie, possibleTransposeWords));
        existingWords.addAll(secondEdit(trie, possibleReplacementWords));
        existingWords.addAll(secondEdit(trie, possibleInsertionWords));

        return existingWords;
    }

    /**
     * Provides a list of left values incremented by one letter for each item
     *
     * @param split a list containing the left and right parts of a word incremented/decremented by one letter
     * @return a list of the left values
     */
    private ArrayList<String> splitLeft(ArrayList<HashMap<String, String>> split) {
        ArrayList<String> left = new ArrayList<>();
        // Compose a left and right list
        for (int i = 0; i <split.size(); ++i) {
            HashMap<String, String> splitWord = split.get(i);
            left.add((String)splitWord.keySet().toArray()[0]);
        }
        return left;
    }

    /**
     * Provides a list of right values decremented by one letter for each item
     *
     * @param split a list containing the left and right parts of a word incremented/decremented by one letter
     * @return a list of the right values
     */
    private ArrayList<String> splitRight(ArrayList<HashMap<String, String>> split) {
        ArrayList<String> right = new ArrayList<>();
        // Compose right list
        for (int i = 0; i <split.size(); ++i) {
            HashMap<String, String> splitWord = split.get(i);
            right.add((String)splitWord.values().toArray()[0]);
        }
        return right;
    }

    /**
     * Determines a list of possible words with one deletion from the misspelled word
     * Citation: http://norvig.com/spell-correct.html deletes = [L + R[1:] for L, R in splits if R]
     *
     * @param left a list containing the left part of a word incremented by one letter
     * @param right a list containing the right part of a word incremented by one letter
     * @return a list of words containing one deletion from the misspelled word
     */
    private ArrayList<String> deletion(ArrayList<String> left, ArrayList<String> right) {
        ArrayList<String> possibleDeleteWords = new ArrayList<>();
        // Concatenate each left and right[1:] element together and append to delete list
        for (int i = 0; i < left.size(); ++i) {
            possibleDeleteWords.add(left.get(i) + right.get(i).substring(1));
        }
        return possibleDeleteWords;
    }

    /**
     * Determines a list of possible words with one transposition from the misspelled word
     * Citation: http://norvig.com/spell-correct.html transposes = [L + R[1] + R[0] + R[2:] for L, R in splits if len(R)>1]
     *
     * @param left a list containing the left part of a word incremented by one letter
     * @param right a list containing the right part of a word incremented by one letter
     * @return a list of words containing one transposition from the misspelled word
     */
    private ArrayList<String> transpose(ArrayList<String> left, ArrayList<String> right) {
        ArrayList<String> possibleTransposedWords = new ArrayList<>();
        // Concatenate each left + right[1] + right[0] + right[2:] element together and append to transpose list
        for (int i = 0; i < left.size() - 2; ++i) {
            possibleTransposedWords.add(left.get(i) + right.get(i).substring(1, 2) + right.get(i).substring(0, 1) + right.get(i).substring(2));
        }
        return possibleTransposedWords;
    }

    /**
     * Determines a list of possible words with one replacement from the misspelled word
     * Citation: http://norvig.com/spell-correct.html replaces = [L + c + R[1:] for L, R in splits if R for c in letters]
     *
     * @param left a list containing the left part of a word incremented by one letter
     * @param right a list containing the right part of a word incremented by one letter
     * @return a list of words containing one replacement from the misspelled word
     */
    private ArrayList<String> replacement(ArrayList<String> left, ArrayList<String> right) {
        ArrayList<String> possibleReplacementWords = new ArrayList<>();
        // Concatenate each left + letters[j] + right[1:] element together and append to replacement list
        for (int i = 0; i < left.size(); ++i) {
            for(int j = 0; j < getLetters().length(); ++j) {
                possibleReplacementWords.add(left.get(i) + getLetters().charAt(j) + right.get(i).substring(1));
            }
        }
        return possibleReplacementWords;
    }

    /**
     * Determines a list of possible words with one insertion from the misspelled word
     * Citation: http://norvig.com/spell-correct.html inserts = [L + c + R for L, R in splits for c in letters]
     *
     * @param left a list containing the left part of a word incremented by one letter
     * @param right a list containing the right part of a word incremented by one letter
     * @return a list of words containing one insertion from the misspelled word
     */
    private ArrayList<String> insertion(ArrayList<String> left, ArrayList<String> right){
        ArrayList<String> possibleInsertionWords = new ArrayList<>();
        // Concatenate each left + letters[j] + right element together and append to insertion list
        for (int i = 0; i < left.size(); ++i) {
            for(int j = 0; j < getLetters().length(); ++j) {
                possibleInsertionWords.add(left.get(i) + getLetters().charAt(j) + right.get(i));
            }
        }
        return possibleInsertionWords;
    }

    /**
     * Determines the known words from the possible words within the deletion, transposition, replacement, and insertion lists
     *
     * @param trie the trie tree to check for existing words in the deletion, transposition, replacement, and insertion lists
     * @param deletion a list of possible words containing one deletion from the misspelled word
     * @param transpose a list of possible words containing one transposition from the misspelled word
     * @param replacement a list of possible words containing one replacement from the misspelled word
     * @param insertion a list of possible words containing one insertion from the misspelled word
     * @return
     */
    private ArrayList<String> knownWords(TrieTree trie, ArrayList<String> deletion, ArrayList<String> transpose, ArrayList<String> replacement, ArrayList<String> insertion) {
        ArrayList<String> existingWords = new ArrayList<>();
        ArrayList<ArrayList<String>> listOfPossibleWords = new ArrayList<>();
        listOfPossibleWords.add(insertion);
        listOfPossibleWords.add(deletion);
        listOfPossibleWords.add(replacement);
        listOfPossibleWords.add(transpose);

        // Search the trie for the element in deletion, transpose, replacement, and insertion lists
        for (ArrayList<String> list : listOfPossibleWords) {
            for (String word : list) {
                boolean isInList = false;
                for (int i = 0; i < existingWords.size(); ++i) {
                    if (word.equals(existingWords.get(i))) {
                        isInList = true;
                        break;
                    }
                }
                if (!isInList) {
                    boolean isInTrie = trie.search(word);
                    if (isInTrie)
                        existingWords.add(word);
                }
            }
        }
        return existingWords;
    }

    /**
     * Determines existing words that are two edit distances away from word
     *
     * @param trie the trie tree to check for existing words
     * @param firstEdit a list containing words with either one deletion, transposition, replacement, or insertion
     * @return A list of known words that are two edit distances away from word
     */
    private ArrayList<String> secondEdit(TrieTree trie, ArrayList<String> firstEdit) {
        ArrayList<String> existingWords = new ArrayList<>();
        for(String word : firstEdit) {
            ArrayList<HashMap<String, String>> split = splitWord(word);
            existingWords.addAll( editDistance1(trie, split));
        }
        return existingWords;
    }
}
