// Programmer: Jacob Tilley
// Date: 8/9/23
// Program: 20 Questions game
// Purpose: Creates the binary tree used in the 20 Questions game.

import java.io.*;
import java.util.Scanner;

public class QuestionTree {
   private QuestionNode root;
   private UserInterface ui;
   private int totalGames = 0;
   private int gamesWon = 0;
   
   // constructor for QuestionTree, which uses the interface methods in QuestionMain
   public QuestionTree(UserInterface ui) {
      this.ui = ui;
      root = new QuestionNode();
   }  // end of QuestionTree constructor      
   
   
   // plays one complete guessing game with the user, asking yes/no questions
   // until reaching an answer
   public void play() {        
      root = playHelper(root);   
   }  // end of play method
   
   
   // used in the play method, uses recursion to ask user questions until an
   // answer node is reached. Then asks user if the answer is what they are thinking of
   private QuestionNode playHelper(QuestionNode root) {
      // base case/answer node
      if (root.left == null && root.right == null) { 
         ui.print("Would your object happen to be " + root.data + "?");
         if (ui.nextBoolean()) { // if answer to question is yes...           
            ui.println("I win!");   // computer wins
            gamesWon++;
         } else {  // if answer to question is no...
            root = handleLosingGame(root);   // user wins, asks for new tree input                                                             
         }  // end of if/else                 
         totalGames++;      
      } else { 
      // recursive case/question node
         ui.print(root.data); // asks a question to user
         if (ui.nextBoolean()) { // if answer to question is yes...
            root.left = playHelper(root.left);  // go down left side of tree   
         } else {
            root.right = playHelper(root.right); // go down right side of tree
         }  // end of if/else
      }  // end of if/else      
      return root;
   }  // end of playHelper method
   
   
   // if the computer does not guess correctly, this method will ask user    
   // for input, then create a new QuestionNode for the next game.
   private QuestionNode handleLosingGame(QuestionNode node) {
      ui.print("I lose, What is your object?");
      String object = ui.nextLine();
      ui.print("Type a yes/no question to distinguish your item from " + 
                                                      node.data + ": ");
      String newQuestion = ui.nextLine();                                                                 
      ui.print("And what is the answer for your object? ");
      boolean answer = ui.nextBoolean();
      
      // populates the questions tree with the new input
      QuestionNode newNode = new QuestionNode(newQuestion);                              
      if (answer) { // if answer == y
         newNode.left = new QuestionNode(object); // constructs new leaf/answer node
         newNode.right = node;   // new location for original answer
      } else {      // if answer == n               
         newNode.left = node;    // new location for original answer
         newNode.right = new QuestionNode(object);   // constructs new leaf/answer node         
      }  // end of if/else
      return newNode;   
   }  // end of handleLosingGame method
   
   
   // saves the question tree used in the game to a file
   public void save(PrintStream output) {
      if (output == null) {
         throw new IllegalArgumentException();
      }
      saveHelper(root, output);
   }  // end of save method
   
   
   // used in the save method, uses recursion to save a new tree using
   // a pre-order traversal
   private void saveHelper(QuestionNode node, PrintStream output) {
      if (node != null) {
         if (node.left != null && node.right != null) { // Question node
            output.println("Q:" + node.data);
            saveHelper(node.left, output); // Save left subtree
            saveHelper(node.right, output); // Save right subtree
         } else { // Answer node
            output.println("A:" + node.data);
         }  // end of if/else
      }
   }  // end of saveHelper method
   
   
   // loads a new question tree from a file
   public void load(Scanner input) {
      if (input == null) {
         throw new IllegalArgumentException();
      } 
      root = loadHelper(input);
   }  // end of load method
   
   
   // used in the load method, uses recursion to populate a new tree using 
   // a pre-order traversal
   private QuestionNode loadHelper(Scanner input) {
      if (!input.hasNextLine()) {   // base case
         return root;
      } else {   // recursive case
         String data = input.nextLine();
         String nodeData = data.substring(2);          
         QuestionNode newNode = new QuestionNode(nodeData);
         String nodeType = data.substring(0,1).toUpperCase();
         // if a question, then create 2 children
         if (nodeType.equals("Q")) {              
            newNode.left = loadHelper(input);
            newNode.right = loadHelper(input);
         }
         return newNode;
      }  // end of if/else     
   }  // end of loadHelper method
   
   
   // returns the total games that have been played in the session
   public int totalGames() {
      return totalGames;
   }  // end of totalGames
   
   
   // returns the games that the computer has won in the session
   public int gamesWon() {
      return gamesWon;
   }  // end of gamesWon method
   
   
}  // end of QuestionTree class