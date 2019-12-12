package edu.isu.cs2235;
import java.util.Scanner;

/**
 * Driver for the spell checker program
 *
 * @author Katherine Wilsdon
 */
public class Driver {

    static Scanner in = new Scanner(System.in);

    /**
     * Runs the program
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("\nLoading dictionary...");
        SpellCheckerTree tree = new SpellCheckerTree("en-US.dic");
        System.out.print("Dictionary loaded.");
        String phrase = "";
        while(phrase != "-1") {
            System.out.println("\n\nEnter a string to spellcheck (\u002D1 to quit): ");
            phrase = in.nextLine();
            if (phrase.equals("-1")) {
                System.out.println("Good bye.");
                break;
            } else {
                tree.spellCheck(phrase);
            }
        }
    }
}
