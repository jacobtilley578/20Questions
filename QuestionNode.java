// Programmer: Jacob Tilley
// Date: 8/9/23
// Program: 20 Questions game
// Purpose: Creates each node used in the binary tree for the 20 Questions game. 

public class QuestionNode {
   String data;
   QuestionNode left;
   QuestionNode right;
   
   // constructs an empty QuestionNode with a default value.
   public QuestionNode() {
      this.data = "computer";
      this.left = null;
      this.right = null;
   }  // end of QuestionNode constructor 1
   
   // @Overrides QuestionNode constructor with a data input
   public QuestionNode(String data) {
      this.data = data;
      this.left = null;
      this.right = null;
   }  // end of QuestionNode constructor 2
   
}  // end of QuestionNode class