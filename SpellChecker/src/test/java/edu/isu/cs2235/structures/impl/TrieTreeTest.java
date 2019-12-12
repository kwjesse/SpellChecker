package edu.isu.cs2235.structures.impl;

import org.junit.*;

import java.util.HashMap;

import static org.junit.Assert.*;

public class TrieTreeTest {

    private TrieTree fixture;

    public TrieTreeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        fixture = new TrieTree();
    }

    @After
    public void tearDown() {
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInsert() {
        fixture.insert(null);
    }

    @Test
    public void testInsert_2() {
        fixture.insert("finally");
        assertTrue("Word not inserted into the tree.", fixture.search("finally"));
    }

    @Test
    public void testInsert_3() {
        fixture.insert("finally");
        TrieNode node = fixture.searchNode("finally");
        assertTrue("Leaf node was not designated to be a leaf.", node.isLastLetter());
    }

    @Test
    public void testInsert_4() {
        fixture.insert("finally");
        fixture.insert("football");
        fixture.insert("finale");
        assertTrue("Word not inserted into the tree.", fixture.search("finale"));
    }

    @Test
    public void testSearch() {
        fixture.insert("flower");
        assertFalse("Word should not be found in the tree.", fixture.search("flowers"));
    }

    @Test
    public void testSearch_2() {
        fixture.insert("flower");
        assertFalse("Word should not be found in the tree.", fixture.search("flowr"));
    }

    @Test
    public void testSearch_3() {
        fixture.insert("coal");
        fixture.insert("coax");
        assertTrue("Word should have been found in the tree.", fixture.search("coal"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testSearchNode(){
        fixture.search(null);
    }

    @Test
    public void testSearchNode_2() {
        fixture.insert("major");
        fixture.insert("mayor");
        TrieNode testNode = fixture.searchNode("major");
        TrieNode providedNode = new TrieNode('r');
        providedNode.setLastLetter(true);
        assertEquals("The returned node is not correct.", providedNode.getElement(), testNode.getElement());
    }

    @Test
    public void testSearchNode_3() {
        fixture.insert("major");
        fixture.insert("mayor");
        TrieNode testNode = fixture.searchNode("major");
        TrieNode providedNode = new TrieNode('r');
        providedNode.setLastLetter(true);
        assertEquals("The returned node is not correct.", providedNode.getChildren(), testNode.getChildren());
    }

    @Test
    public void testSearchNode_4() {
        fixture.insert("major");
        fixture.insert("mayor");
        TrieNode testNode = fixture.searchNode("major");
        TrieNode providedNode = new TrieNode('r');
        providedNode.setLastLetter(true);
        assertEquals("The returned node is not correct.", providedNode.isLastLetter(), testNode.isLastLetter());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testStartsWith(){
        fixture.search(null);
    }

    @Test
    public void testStartsWith_2() {
        fixture.insert("coal");
        fixture.insert("coax");
        fixture.insert("flower");
        HashMap<Character, TrieNode> children = fixture.startsWith("coa");
        TrieNode testNode = children.get('l');
        TrieNode providedNode = new TrieNode('l');
        providedNode.setLastLetter(true);
        assertEquals("The prefix should have been found in the tree and returned its children.", providedNode.getElement(), testNode.getElement());
    }

    @Test
    public void testStartsWith_3() {
        fixture.insert("coal");
        fixture.insert("coax");
        fixture.insert("flower");
        HashMap<Character, TrieNode> children = fixture.startsWith("coa");
        TrieNode testNode = children.get('x');
        TrieNode providedNode = new TrieNode('x');
        providedNode.setLastLetter(true);
        assertEquals("The prefix should have been found in the tree and returned its children.", providedNode.getElement(), testNode.getElement());
    }

    @Test
    public void testStartsWith_4() {
        fixture.insert("coal");
        fixture.insert("coax");
        fixture.insert("flower");
        HashMap<Character, TrieNode> children = fixture.startsWith("coc");
        assertNull("The prefix is not in the tree and should have returned null.", children);
    }
}
