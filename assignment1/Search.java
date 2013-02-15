import java.io.*;
import java.util.*;
import java.lang.*;

public class Search
{

/**
	* @param args
	*/

	/* The set of states sigma is a vector of integers */
	static Vector sigma = new Vector<Integer> () ;

	/* The binary relation succ over sigma is a vector of pairs of integers */
	static Vector succ = new Vector<int[]> () ;

	/*	Hashmap Size for hashing function	*/
	static int hashSize = 16 ;

	/**
		* printSearchGraph displays the binary relation succ on the screen
		* using the format: start -->> end to say that (start, end) belongs
		* to succ
		*/
	public static void printSearchGraph ()
	{
		Iterator<int[]> itSucc = succ.iterator() ;

		for ( ; itSucc.hasNext() ; )
		{
			int[] pair = itSucc.next() ;
			System.out.println ( pair[0] + "-->>" + pair[1] ) ;
		}
	}

	/**
	 * readData reads the description of the graph into the Vectors
	 * sigma and succ
	 */
	public static void readData ( String fname )
	{
		Scanner inFile = null ;

		try
		{
			inFile = new Scanner ( new File ( fname ) ) ;

			while ( inFile.hasNextLine() )
			{
				int[] t = new int[2] ;
				t[0] = inFile.nextInt() ;
				t[1] = inFile.nextInt() ;
				succ.addElement( t ) ;
				if ( sigma.lastIndexOf( t[0] ) == -1 )
					sigma.addElement( t[0] ) ;

				if ( sigma.lastIndexOf( t[1] ) == -1 )
					sigma.addElement( t[1] ) ;
			}
		}
		catch ( IOException e )
		{
			// If another exception is generated, print a stack trace
			e.printStackTrace() ;
		}
		finally
		{
			if ( inFile != null )
				inFile.close() ;
		}
	}

	/**
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
	public static void bfs ( int init , int goal )
	{
		//	Initialize Frontier
		Queue<SearchNode> frontier = new LinkedList<SearchNode>() ;
		//	Initialize Explored
		HashMap explored = new HashMap ( hashSize ) ;
		//	Add initial node
		frontier.add( new SearchNode ( init ) ) ;
		int nodesCreated = 1 ;
		int statesVisited = 0 ;

		while ( !frontier.isEmpty() )
		{
			SearchNode node = frontier.remove() ;
			if ( node.getState() == goal )
			{
				System.out.print ( "A path exists!\n\t" ) ;
				SearchNode path = node ;
				while ( path.getParent() != null )
				{
					System.out.print ( path.getState() + " <- " ) ;
					path = path.getParent() ;
				}
				System.out.println( path.getState() ) ;
				System.out.println ( "\n\tCreated " + nodesCreated + " nodes." ) ;
				System.out.println ( "\tVisited " + statesVisited + " states.\n" ) ;
				return ;
			}
			else
			{
				explored.put ( node.getState() % hashSize , node.getState() ) ;
				nodesCreated++ ;

				Iterator<int[]> itSucc = succ.iterator() ;

				for ( ; itSucc.hasNext() ; )
				{
					int[] pair = itSucc.next() ;
					if ( pair[0] == node.getState() )
					{
						if ( explored.get( pair[1] % hashSize ) != pair[1] )
						{
							frontier.add( new SearchNode ( pair[1] , node ) ) ;
							statesVisited++ ;
						}
					}
				}
			}
		}
		System.out.println ( "FALSE" ) ;
		System.out.println ( "Created " + nodesCreated + " nodes." ) ;
		System.out.println ( "Visited " + statesVisited + " states." ) ;
	}

	/**
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
	public static void dfs ( int init , int goal )
	{
		//	Initialize Frontier
		Stack<SearchNode> frontier = new Stack<SearchNode>() ;
		//	Initialize Explored
		HashMap explored = new HashMap ( hashSize ) ;
		//	Add initial node
		frontier.push( new SearchNode ( init ) ) ;
		int nodesCreated = 1 ;
		int statesVisited = 0 ;

		while ( !frontier.isEmpty() )
		{
			SearchNode node = frontier.pop() ;
			if ( node.getState() == goal )
			{
				System.out.print ( "A path exists!\n\t" ) ;
				SearchNode path = node ;
				while ( path.getParent() != null )
				{
					System.out.print ( path.getState() + " <- " ) ;
					path = path.getParent() ;
				}
				System.out.println( path.getState() ) ;
				System.out.println ( "\n\tCreated " + nodesCreated + " nodes." ) ;
				System.out.println ( "\tVisited " + statesVisited + " states." ) ;
				return ;
			}
			else
			{
				explored.put ( node.getState() % hashSize , node.getState() ) ;
				nodesCreated++ ;

				Iterator<int[]> itSucc = succ.iterator() ;

				for ( ; itSucc.hasNext() ; )
				{
					int[] pair = itSucc.next() ;
					if ( pair[0] == node.getState() )
					{
						if ( explored.get( pair[1] % hashSize ) != pair[1] )
						{
							frontier.add( new SearchNode ( pair[1] , node ) ) ;
							statesVisited++ ;
						}
					}
				}
			}
		}
		System.out.println ( "FALSE" ) ;
		System.out.println ( "Created " + nodesCreated + " nodes." ) ;
		System.out.println ( "Visited " + statesVisited + " states." ) ;
	}

	public static void main ( String[] args ) throws IOException
	{
		readData ( args[0] ) ;
		System.out.println ( "States: " + sigma ) ;
		System.out.println ( "Relation succ: " ) ;
		printSearchGraph () ;
		int init = Integer.valueOf ( args[1] ) ;
		int goal = Integer.valueOf ( args[2] ) ;
		System.out.println ( "\nCall BFS: from " + init + " to " + goal ) ;
		bfs ( init , goal ) ;
		System.out.println ( "Call DFS: from " + init + " to " + goal ) ;
		dfs ( init , goal ) ;
	}
}