/*
 * state node class 
 * this class defines the states and 
 * the functions action, terminal, utility  
 * for the tic-tac-toe game 
 */

import java.util.*;

public class State {

	private int[][] content;   // the configuration of the state 
	private int player;        // the player who has the move   

	
	public State() {           // an empty state    
		content = new int[3][3];
    	for (int i=0; i < 3; i++) 
    		for (int j=0; j< 3; j++)  
    			content[i][j] = 0;
    	player = 1; 
	}
	
	/*
	 * the next constructor defines a configuration of the game 
	 * in the following way:
	 *      a b c 
	 *      d e f 
	 *      g h i
	 * assume that each letter stands for a number (1 or 2 or 0)  
	 * with 0 denotes the blank 
	 * 1 is for X player (max) 
	 * 2 is for o player (min) 
         * (ignoring wrong numbers!) 
	 */
	
	public State(int a, int b, int c, int d, int e, int f, int g, int h, int i) {
		content = new int[3][3];
		content[0][0] = a; 
		content[0][1] = b; 
		content[0][2] = c; 
		content[1][0] = d; 
		content[1][1] = e; 
		content[1][2] = f; 
		content[2][0] = g; 
		content[2][1] = h; 
		content[2][2] = i; 
    		player = 1; 
	}
	
	// return the current player 
	public int getPlayer() {
		return player; 
	}
	
	// set who has the next move 
	
	public void setPlayer(int c) {
		player = c;  
	}
	
	// computing the result of an action 
	// of placing a symbol at the location (x,y) 
	
	public State result(int player, int x, int y) {
		State s = this.copy(); 
		if (player == 1) {
			s.setAt(1, x, y);
			s.setPlayer(2);
		}
		else {
			s.setAt(2, x, y);
			s.setPlayer(1);
		}
		return s;
	}

	// set the value of a square on the board 
	
	public void setAt(int value, int x, int y) {
		content[x][y] = value;
	}
	
	
	// copy a state 
	
    public State copy() {
    	State n = new State();
    	for (int i=0; i < 3; i++) {
    		for (int j=0; j< 3; j++) { 
    			n.content[i][j] = content[i][j];
    		}
    	}	
    	n.player = player;
    	return n; 
    }

    // checking for equality between two states 
    public boolean equal(State n) {
    	for (int i=0; i < 3; i++) 
    		for (int j=0; j< 3; j++)  
    			if (n.content[i][j] != content[i][j]) return false;
    	return true; 
    }
    
    // checking whether a state is a final state of the game 
    
    public boolean terminal() {
    	
    	int count = 0; 
   	
    	// equal row 
    	for (int i=0; i < 3; i++) {
    		if (content[i][0] == content[i][1] &&
    				content[i][0] == content[i][2] && 
    						content[i][0] != 0
    				 ) return true;     		
    	}
    	
    	// equal column 

    	for (int i=0; i < 3; i++) {
    		if (content[0][i] == content[1][i] &&
    				content[0][i] == content[2][i]	&& 
    						content[0][i] != 0
    				 ) return true;     		
    	}
    	
    	// equal diagonal top left to bottom right
    	if (content[0][0] == content[1][1] && 
    			content[0][0] == content[2][2] && 
						content[0][0] != 0
   	 				 ) return true;

    	// equal diagonal top right to bottom left
       	if (content[2][0] == content[1][1] && 
    			content[2][0] == content[0][1] && 
						content[2][0] != 0
   	 				 ) return true;

    	// all cells are occupied 
    	
    	for (int i=0; i < 3; i++) 
    		for (int j=0; j< 3; j++)  
    			if (content[i][j] != 0) count ++;
    	
    	if (count == 9) return true; 
    	
    	return false; 
    }
   
    // computing the utility of a state (from MAX point of view) 

    public int utility() {
    	
    	int count = 0; 
   	
    	// equal row 
    	for (int i=0; i < 3; i++) {
    		if (content[i][0] == content[i][1] &&
    				content[i][0] == content[i][2] && 
    						content[i][0] != 0
    				 ) {
    			if (content[i][0] == 1) return 1;
    			return -1;     		
    		}
    	}
    	
    	// equal column 

    	for (int i=0; i < 3; i++) {
    		if (content[0][i] == content[1][i] &&
    				content[0][i] == content[2][i]	&& 
    						content[0][i] != 0
    				 ) {
    			if (content[0][i] == 1) return 1;
    			return -1;     		
    		} 
    	}
    	
    	// equal diagonal top left to bottom right  
    	if (content[0][0] == content[1][1] && 
    			content[0][0] == content[2][2] && 
						content[0][0] != 0
   	 				 ) {
			if (content[0][0] == 1) return 1;
			return -1;     		
		} 

    	// equal diagonal top right to bottom left
       	if (content[0][2] == content[1][1] && 
    			content[0][2] == content[2][0] && 
						content[0][2] != 0
   	 				 ) {
			if (content[2][0] == 1) return 1;
			return -1;     		
		}  
    	
    	return 0;     		
		
    }
   
    // identifying the eligible moves for the current state 
    // returns a vector of pairs of integers, each pair specifies 
    // a location where the player can mark 

    public Vector action() {
    	Vector actions = new Vector<int[]> ();

    	for (int i=0; i < 3; i++) 
    		for (int j=0; j< 3; j++) { 
    			int[] a = new int[2];
    			if (content[i][j] == 0) {
    				a[0] = i; 
    				a[1] = j; 
    				actions.add(a);
    			}
    		}
    	
    	return actions; 
    }
    
    // display the content of a state 
    
    public String toString() {
    	return " "+ content[0][0]+ " "+ content[0][1]+" "+ content[0][2]+"\n"+ 
    	       " "+ content[1][0]+ " "+ content[1][1]+" "+ content[1][2]+"\n"+ 
    	       " "+ content[2][0]+ " "+ content[2][1]+" "+ content[2][2]+"\n"; 
    }
}
