package edu.isu.cs2235;

import edu.isu.cs2235.structures.impl.SpellChecker;
import edu.isu.cs2235.structures.impl.TrieTree;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * A very simple spell checker tree example using a TrieTree and console
 * input.
 *
 * @author Katherine Wilsdon
 */
public class SpellCheckerTree {
    private TrieTree trie;
    private SpellChecker spellChecker;
    Scanner scanner = new Scanner(System.in);

    /**
     * Constructs a new spell checker tree class which manages an underlying spell checker
     * tree
     * @param file the dictionary of words
     */
    public SpellCheckerTree (String file) {
        trie = new TrieTree();
        spellChecker = new SpellChecker();
        load(file);
    }

    /**
     * Loads a dictionary from the given file
     * @param file the file name to load
     */
    public void load(String file) {
        try {
            // Get a list containing each line in the file
            Path path = Paths.get(file);
            List<String> lines = Files.readAllLines(path);
            StringBuilder letters = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
            for (String line : lines) {
                // Inserts the word into the trie tree
                trie.insert(line);
                // Adds additional letters to a string of letters
                for (int i = 0; i < line.length(); ++i) {
                    boolean isInLetters = false;
                    for (int j = 0; j < letters.length(); ++j) {
                        if (line.charAt(i) == letters.charAt(j))
                            isInLetters = true;
                    }
                    if (!isInLetters) {
                        letters.append(line.charAt(i));
                    }
                }
            }
            // Sets the set of letters in the spell checker class
            String letterSet = new String(letters);
            this.spellChecker.setLetters(letterSet);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Determines the misspelled words, finds alternative spelling options, and
     * prints the final phrase to the console.
     *
     * @param phrase the phrase to spell check
     */
    public void spellCheck(String phrase) {
        System.out.println("\nChecking Spelling...");
        String[] wordsAndPunctuation = phrase.split("\\b");
        String[] fixedPhrase = phrase.split("\\b");
        Boolean[] isWordMisspelled = new Boolean[wordsAndPunctuation.length];
        Arrays.fill(isWordMisspelled, Boolean.FALSE);
        StringBuilder punctuation = new StringBuilder(" .?!,:;-[]{}()'\"*`@#$%^&_+=|\\<>/");
        int numOfMisspelledWords = 0;

        // Find all the punctuation marks in the phrase
        for (int i = 0; i < wordsAndPunctuation.length; ++i){
            boolean isPunctuation = false;
            for (int j = 0; j < punctuation.length(); ++j){
                for (int k = 0; k < wordsAndPunctuation[i].length(); ++k){
                    if (wordsAndPunctuation[i].charAt(k) == punctuation.charAt(j)) {
                        isPunctuation = true;
                        break;
                    }
                }
            }
            if (isPunctuation)
                continue;
            // Searches for all words that are not punctuation
            else {
                boolean isInTree = this.trie.search(wordsAndPunctuation[i]);
                if (!isInTree) {
                    isInTree = this.trie.search(wordsAndPunctuation[i].toLowerCase());
                }
                if (!isInTree) {
                    isWordMisspelled[i] = true;
                    numOfMisspelledWords++;
                }
            }
        }
        // When there are no spelling mistakes
        if (numOfMisspelledWords == 0) {
            System.out.println("There are no misspelled words in the provided phrase.");
        } // When there is at least one spelling mistake
        else if (numOfMisspelledWords > 0) {
            for (int i = 0; i < isWordMisspelled.length; i++) {
                if (isWordMisspelled[i]) {
                    ArrayList<String> corrections = new ArrayList<>();
                    // If there are less than or equal to 5 (the number of options)
                    if (wordsAndPunctuation.length <= 5) {
                        System.out.print("\nMisspelling found: \"" + wordsAndPunctuation[i] + "\" in ... ");
                        for (String word : wordsAndPunctuation) {
                            System.out.print(word);
                        }
                        System.out.print(" ...");
                        corrections.addAll(possibleWords(wordsAndPunctuation[i].toLowerCase()));
                        String newWord = printPossibilities(corrections, wordsAndPunctuation[i]);
                        // If the user did not specify to skip the word, replace the word in the array and print the replacement to console
                        if (newWord != null) {
                            fixedPhrase[i] = newWord;
                            System.out.print("\nReplaced \"...");
                            for (String word : wordsAndPunctuation) {
                                System.out.print(word);
                            }
                            System.out.print(" ...\" with \"...");
                            for (String word : fixedPhrase) {
                                System.out.print(word);
                            }
                            System.out.print("...\"");
                        }
                    } // If i is in the middle of the word where i is at least or at most 2 from each end of the word
                    else if (i > 1 && i < wordsAndPunctuation.length - 2) {
                        System.out.println("\nMisspelling found: \"" + wordsAndPunctuation[i] + "\" in ... " + wordsAndPunctuation[i - 2] + wordsAndPunctuation[i - 1] + wordsAndPunctuation[i] + wordsAndPunctuation[i + 1] + wordsAndPunctuation[i + 2] + " ...");
                        corrections.addAll(possibleWords(wordsAndPunctuation[i].toLowerCase()));
                        String newWord = printPossibilities(corrections, wordsAndPunctuation[i]);
                        // If the user did not specify to skip the word, replace the word in the array and print the replacement to console
                        if (newWord != null) {
                            fixedPhrase[i] = newWord;
                            System.out.println("\nReplaced \"..." + wordsAndPunctuation[i - 2] + wordsAndPunctuation[i - 1] + wordsAndPunctuation[i] + wordsAndPunctuation[i + 1] + wordsAndPunctuation[i + 2] + " ...\" with \"..." + fixedPhrase[i - 2] + fixedPhrase[i - 1] + fixedPhrase[i] + fixedPhrase[i + 1] + fixedPhrase[i + 2] + "...\"");
                        }
                    } // If i is the the first letter of the word
                    else if (i ==0) {
                        System.out.println("\nMisspelling found: \"" + wordsAndPunctuation[i] + "\" in ... " + wordsAndPunctuation[i] + wordsAndPunctuation[i + 1] +  wordsAndPunctuation[i + 2] +  wordsAndPunctuation[i + 3] +  wordsAndPunctuation[i + 4]+ " ...");
                        corrections.addAll(possibleWords(wordsAndPunctuation[i].toLowerCase()));
                        String newWord = printPossibilities(corrections, wordsAndPunctuation[i]);
                        // If the user did not specify to skip the word, replace the word in the array and print the replacement to console
                        if (newWord != null) {
                            fixedPhrase[i] = newWord;
                            System.out.println("\nReplaced \"..." + wordsAndPunctuation[i] + wordsAndPunctuation[i + 1] + wordsAndPunctuation[i + 2] + wordsAndPunctuation[i + 3] +  wordsAndPunctuation[i + 4] + "...\" with \"..." + fixedPhrase[i]  + fixedPhrase[i+1] + fixedPhrase[i+2] + fixedPhrase[i + 3] +  fixedPhrase[i + 4] + "...\"");
                        }
                    } // If i is the second letter of the word
                    else if (i == 1) {
                        System.out.println("\nMisspelling found: \"" + wordsAndPunctuation[i-1] + wordsAndPunctuation[i] + "\" in ... " + wordsAndPunctuation[i] + wordsAndPunctuation[i + 1] +  wordsAndPunctuation[i + 2] +  wordsAndPunctuation[i + 3] + " ...");
                        corrections.addAll(possibleWords(wordsAndPunctuation[i].toLowerCase()));
                        String newWord = printPossibilities(corrections, wordsAndPunctuation[i]);
                        // If the user did not specify to skip the word, replace the word in the array and print the replacement to console
                        if (newWord != null) {
                            fixedPhrase[i] = newWord;
                            System.out.println("\nReplaced \"..." + wordsAndPunctuation[i-1] + wordsAndPunctuation[i] + wordsAndPunctuation[i + 1] + wordsAndPunctuation[i + 2] + wordsAndPunctuation[i + 3] + "...\" with \"..." + fixedPhrase[i-1] + fixedPhrase[i]  + fixedPhrase[i+1] + fixedPhrase[i+2] + fixedPhrase[i + 3] + "...\"");
                        }
                    } // If i is the last letter of the word
                    else if (i == wordsAndPunctuation.length - 1) {
                        System.out.println("\nMisspelling found: \"" + wordsAndPunctuation[i] + "\" in ... " + wordsAndPunctuation[i-4] + wordsAndPunctuation[i-3] + wordsAndPunctuation[i - 2] +  wordsAndPunctuation[i - 1] + wordsAndPunctuation[i] + " ...");
                        corrections.addAll(possibleWords(wordsAndPunctuation[i].toLowerCase()));
                        String newWord = printPossibilities(corrections, wordsAndPunctuation[i]);
                        // If the user did not specify to skip the word, replace the word in the array and print the replacement to console
                        if (newWord != null){
                            fixedPhrase[i] = newWord;
                            System.out.println("\nReplaced \"..." + wordsAndPunctuation[i-4] + wordsAndPunctuation[i-3] + wordsAndPunctuation[i - 2] + wordsAndPunctuation[i - 1] + wordsAndPunctuation[i]+ " ...\" with \"..." + fixedPhrase[i-4] + fixedPhrase[i-3] + fixedPhrase[i-2] + fixedPhrase[i-1] + fixedPhrase[i] + "...\"");
                        }
                    } // If i is the second to last letter of the word
                    else if (i == wordsAndPunctuation.length - 2){
                        System.out.println("\nMisspelling found: \"" + wordsAndPunctuation[i] + "\" in ... " + wordsAndPunctuation[i-3] + wordsAndPunctuation[i - 2] +  wordsAndPunctuation[i - 1] + wordsAndPunctuation[i] + wordsAndPunctuation[i+1] + " ...");
                        corrections.addAll(possibleWords(wordsAndPunctuation[i]));
                        String newWord = printPossibilities(corrections, wordsAndPunctuation[i].toLowerCase());
                        // If the user did not specify to skip the word, replace the word in the array and print the replacement to console
                        if (newWord != null){
                            fixedPhrase[i] = newWord;
                            System.out.println("\nReplaced \"..." + wordsAndPunctuation[i-3] + wordsAndPunctuation[i - 2] + wordsAndPunctuation[i - 1] + wordsAndPunctuation[i] + wordsAndPunctuation[i+1] + " ...\" with \"..." + fixedPhrase[i-3] + fixedPhrase[i-2] + fixedPhrase[i-1] + fixedPhrase[i] + fixedPhrase[i + 1] + "...\"");
                        }
                    }
                }
            }
            // Print the resulting phrase after the misspelled words are corrected
            System.out.println("\nSpelling Check Complete: ");
            System.out.print("Results: ");
            for (int i = 0; i < fixedPhrase.length; ++i){
                System.out.print(fixedPhrase[i]);
            }

        }

    }

    /**
     * Returns a list of corrections that are 1 and 2 edit distances from the misspelled word
     *
     * @param word the misspelled word
     * @return a list of the possible words found in the provided dictionary
     */
    private ArrayList<String> possibleWords(String word){
        ArrayList<String> corrections = new ArrayList<>();
        ArrayList<HashMap<String, String>> split = this.spellChecker.splitWord(word);
        corrections.addAll(this.spellChecker.editDistance1(this.trie, split));
        corrections.addAll(this.spellChecker.editDistance2(this.trie, split));
        return corrections;
    }

    /**
     * Prints the possibilities to the console and returns the chosen response
     *
     * @param possibleWords a list of the possible words found in the provided dictionary
     * @param misspelledWord the word that is misspelled
     * @return the word chosen to replace the misspelled word or null if to skip the replacement
     */
    private String printPossibilities(ArrayList<String> possibleWords, String misspelledWord){
        ArrayList<String> possibilities;
        // Capitalize all responses if the original word was capitalized
        if (misspelledWord.charAt(0) >= 'A' && misspelledWord.charAt(0) <= 'Z')
            possibilities = capitalizeFirstLetter(possibleWords);
        else
            possibilities = possibleWords;
        System.out.println("Replace With: ");
        // When there are more than 5 possible words, print the first 5
        if (possibilities.size() > 5){
            for (int i = 0; i < 5; ++i){
                int number = i + 1;
                System.out.println(number + ". " + possibilities.get(i));
            }
            System.out.println("6. Manual Entry");
            System.out.println("7. Skip");
            System.out.println("\nChoice: ");
            int output = Integer.parseInt(scanner.next());
            // Returns the chosen response
            if (output <= 5) {
                return possibilities.get(output - 1);
            } else if (output == 6){
                System.out.println("\nManual Replacement for \"" + misspelledWord + "\": ");
                return scanner.next();
            } else {
                return null;
            }
        } // When there are less than or equal to 5 possible words, print the all the words
        else {
            int i = 0;
            for (i = 0; i < possibilities.size(); ++i) {
                int number = i + 1;
                System.out.println(number + ". " + possibilities.get(i));
            }
            i += 2;
            System.out.println(i + ". Manual Entry");
            i++;
            System.out.println(i + ". Skip");
            System.out.println("\nChoice: ");
            int output = Integer.parseInt(scanner.next());
            // Returns the chosen response
            if (output <= possibilities.size()) {
                return possibilities.get(output - 1);
            } else if (output == possibilities.size() + 1){
                System.out.print("\nManual Replacement for \"" + misspelledWord + "\": ");
                return scanner.next();
            } else {
                return null;
            }
        }
    }

    /**
     * Capitalizes the first letter in each word in the list
     *
     * @param list of words to capitalize the first letter
     * @return a list of words with the first letter capitalized
     */
    private ArrayList<String> capitalizeFirstLetter(ArrayList<String> list){
        ArrayList<String> capitalizedFirstLetterList = new ArrayList<>();
        for (int i = 0; i < list.size(); ++i) {
            StringBuilder str = new StringBuilder();
            str.append(Character.toUpperCase(list.get(i).charAt(0)));
            str.append(list.get(i).substring(1).toLowerCase());
            String capitalizedWord = new String(str);
            capitalizedFirstLetterList.add(capitalizedWord);
        }
        return capitalizedFirstLetterList;
    }

}
