// Programmer: unknown, with modifications by Jacob Tilley
// Date: 8/9/23
// Program: 20 Questions game
// Purpose: Runs the 20 Questions game with user in the console using a
//          binary search tree.

import java.io.*;
import java.util.Scanner;

/** A basic text user interface for the 20 questions game. */
public class QuestionMain implements UserInterface {
    public static void main(String[] args) {
        QuestionMain tq = new QuestionMain();
        tq.run();
    }
        
    // fields
    private Scanner console;
    private QuestionTree tree;
    
    /** Constructs a text user interface and its question tree. */
    public QuestionMain() {
        console = new Scanner(System.in);
        tree = new QuestionTree(this);
    }
    
    /**
     * Returns the user's response as a String.
     */
    public String nextLine() {
        return console.nextLine();
    }

    /** Prints the given string to the console. */
    public void print(String message) {
        System.out.print(message);
        System.out.print(" ");
    }

    /** Prints the given string to the console. */
    public void println(String message) {
        System.out.println(message);
    }

    /** Prints a blank line to the console. */
    public void println() {
        System.out.println();
    }

    /**
     * Waits for the user to answer a yes/no question on the console and returns the
     * user's response as a boolean (true for anything that starts with "y" or "Y").
     */
    public boolean nextBoolean() {
        String answer = console.nextLine();
        return answer.trim().toLowerCase().startsWith("y");
    }
    
    // private helper for overall game(s) loop
    private void run() {
        println("Welcome to the game of 20 Questions!\n");
        println("Think of an item or thing related to the file you import,");
        println("and the game will try to guess it. To load a questions tree,");
        println("try recalling 'animals.txt' and think of any animal. Or you can");        
        println("skip this step and make your own tree with your own questions.\n");                
        load();
        
        // "Think of an item, and I will guess it in N tries."
        println("\n" + BANNER_MESSAGE);
            
        do {
            // play one complete game
            println();      // blank line between games
            tree.play();
            print(PLAY_AGAIN_MESSAGE);
        } while (nextBoolean());   // prompt to play again
        
        // print overall stats
        // Games played: N ...  I have won: M
        println("\n" + String.format(STATUS_MESSAGE,
                tree.totalGames(), tree.gamesWon()));

        save();
    }
    
    // common code for asking the user whether they want to save or load
    private void load() {
        print(LOAD_MESSAGE);
        if (nextBoolean()) {
            print(SAVE_LOAD_FILENAME_MESSAGE);
            String filename = nextLine();
            try {
                Scanner in = new Scanner(new File(filename));
                tree.load(in);
            } catch (FileNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    // common code for asking the user whether they want to save or load
    private void save() {
        print(SAVE_MESSAGE);
        if (nextBoolean()) {
            print(SAVE_LOAD_FILENAME_MESSAGE);
            String filename = nextLine();
            try {
                PrintStream out = new PrintStream(new File(filename));
                tree.save(out);
                out.close();
            } catch (FileNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}