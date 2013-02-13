import java.io.*;
import java.util.*;
import java.lang.*;

public class Search {

	/**
	 * @param args
	 */

	/* The set of states sigma is a vector of integers */
	static Vector sigma = new Vector<Integer> ();

	/* The binary relation succ over sigma is a vector of pairs of integers */

	static Vector succ = new Vector<int[]> ();

    /*
     * printSearchGraph displays the binary relation succ on the screen
     * using the format: start -->> end to say that (start, end) belongs
     * to succ
    */
    public static void printSearchGraph() {
    	 Iterator<int[]> itSucc = succ.iterator();

         for (; itSucc.hasNext(); ) {
        	 int[] pair = itSucc.next();
        	 System.out.println(pair[0] + "-->>" + pair[1]);
         }
    }

    /*
     * readData reads the description of the graph into the Vectors
     * sigma and succ
     */

    public static void readData(String fname) {

		Scanner inFile = null;

		try {
			inFile = new Scanner (new File(fname));

			while (inFile.hasNextLine()) {
				int[] t = new int[2];
				t[0] = inFile.nextInt();
				t[1] = inFile.nextInt();
				succ.addElement(t);
				if (sigma.lastIndexOf(t[0]) == -1) {
					sigma.addElement(t[0]);
				}
				if (sigma.lastIndexOf(t[1]) == -1) {
					sigma.addElement(t[1]);
				}
			}
		}
		catch (IOException e){
		    // If another exception is generated, print a stack trace
	            e.printStackTrace();
	        }
		finally {
			if (inFile != null) inFile.close();
		}

    }

    /*
     * You need to implement this method:
     * bfs: breadth first search
     * parameters: init - the initial state
     *             goal - the goal state
     * 1. Use the SearchNode class to create the nodes that the search
     *    generates and stores in the frontier
     * 2. Output a solution if there is one path connecting the
     *    initial state and the goal state
     *    Output FALSE if there is no solution
     * 3. Record and display the following statistics:
     *    The number of nodes that the search creates
     *    The number of states that the search visited      *
     */
    public static void bfs(int init, int goal) {

    }

    /*
     * You need to implement this method:
     * dfs: deepth first search
     * parameters: init - the initial state
     *             goal - the goal state
     * 1. Use the SearchNode class to create the nodes that the search
     *    generates and stores in the frontier
     * 2. Output a solution if there is one path connecting the
     *    initial state and the goal state
     *    Output FALSE if there is no solution
     * 3. Record and display the following statistics:
     *    The number of nodes that the search creates
     *    The number of states that the search visited      *
     */

    public static void dfs(int init, int goal) {

    }

	public static void main(String[] args) throws IOException {

        readData(args[0]);
		System.out.println("States: "+sigma);
		System.out.println("Relation succ: ");
		printSearchGraph();
        int init = Integer.valueOf(args[1]);
        int goal = Integer.valueOf(args[2]);
        System.out.println("Call BFS: from " + init + " to " + goal);
        bfs(init, goal);
        System.out.println("Call DFS: from " + init + " to " + goal);
        dfs(init, goal);

	}


}