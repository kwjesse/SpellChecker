package edu.isu.cs2235.structures.impl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class SpellCheckerTest {

    private SpellChecker fixture;

    public SpellCheckerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        fixture = new SpellChecker();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void editDistance1DeleteTest() {

        TrieTree trie = new TrieTree();
        trie.insert("arranged");

        ArrayList<HashMap<String, String>> split = fixture.splitWord("arrainged");
        ArrayList<String> test = fixture.editDistance1(trie, split);
        assertEquals("Correct word was not found", "arranged", test.get(0));

    }

    @Test
    public void editDistance1TransposeTest() {

        TrieTree trie = new TrieTree();
        trie.insert("poetry");

        ArrayList<HashMap<String, String>> split = fixture.splitWord("peotry");
        ArrayList<String> test = fixture.editDistance1(trie, split);
        assertEquals("Correct word was not found", "poetry", test.get(0));
    }

    @Test
    public void editDistance1ReplacementTest() {

        TrieTree trie = new TrieTree();
        trie.insert("bicycle");

        ArrayList<HashMap<String, String>> split = fixture.splitWord("bycycle");
        ArrayList<String> test = fixture.editDistance1(trie, split);
        assertEquals("Correct word was not found", "bicycle", test.get(0));
    }

    @Test
    public void editDistance1InsertionTest() {

        TrieTree trie = new TrieTree();
        trie.insert("spelling");

        ArrayList<HashMap<String, String>> split = fixture.splitWord("speling");
        ArrayList<String> test = fixture.editDistance1(trie, split);
        assertEquals("Correct word was not found", "spelling", test.get(0));
    }

    @Test
    public void editDistance2ReplaceTwice() {

        TrieTree trie = new TrieTree();
        trie.insert("corrected");

        ArrayList<HashMap<String, String>> split = fixture.splitWord("korrectud");
        ArrayList<String> test = fixture.editDistance2(trie, split);
        assertEquals("Correct word was not found", "corrected", test.get(0));
    }

    @Test
    public void editDistance2InsertTwice() {

        TrieTree trie = new TrieTree();
        trie.insert("inconvenient");

        ArrayList<HashMap<String, String>> split = fixture.splitWord("inconvient");
        ArrayList<String> test = fixture.editDistance2(trie, split);
        assertEquals("Correct word was not found", "inconvenient", test.get(0));
    }

    @Test
    public void editDistance2TransposeDelete() {

        TrieTree trie = new TrieTree();
        trie.insert("poetry");

        ArrayList<HashMap<String, String>> split = fixture.splitWord("peotryy");
        ArrayList<String> test = fixture.editDistance2(trie, split);
        assertEquals("Correct word was not found", "poetry", test.get(0));
    }
}

