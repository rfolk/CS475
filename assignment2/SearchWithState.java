import java.util.Iterator ;
import java.util.LinkedList ;
import java.util.PriorityQueue ;
import java.util.Vector ;
import java.util.Comparator ;

public class SearchWithState
{

	/**
	 * checking for visited states
	 */
	public static boolean isVisited(Vector<State> visited, State s)
	{
		for ( int i = 0 ; i < visited.size() ; i++ )
		{
			State n = visited.elementAt( i ) ;
			if ( n.equal( s ) == true )
				return true ;
		}

		return false ;
	}

	/**
		*	I have modified this to be applicable for both A* and Greedy Best First
		*	Search as well as the Manhattan Heuristic and the Misplaced Heuristic.
		*
		*	This is an implementation of the A* algorithm
		*	parameters:	init	- the initial state
		*						 	goal	- the goal state
		*							stype	-	the search type (0 for A*, 1 for Greedy)
		*							heur	- the heuristic to use (0 for misplaced, 1 for Manhattan)
		* 1. We use the NodeWithState class to create the nodes that the search
		*    generates and stores in the frontier
		* 2. The State class contains method for generating states
		*    and computing the successor states of an action
		* 3. The implementation uses a priority queue to
		*    implement the frontier
		*    The number of nodes that the search creates
		*    The number of states that the search visited      *
		*/

	public static void astar ( State init , State goal , int stype , int heur )
	{
		/*
		 *	Since the only difference between the greedy best first search and the
		 *	A* search is the comparator I have implemented a quick check of it in
		 *	the function call which allows this code to run with one function.
		 */
		Comparator<NodeWithState> comparator ;
		if ( stype == 0 )
			comparator = new StateComparator() ;			//	is an A* search
		else
			comparator	= new GBFSStateComparator() ;	//	is a GBFS
		PriorityQueue<NodeWithState> frontier = new PriorityQueue<NodeWithState> ( 10 , comparator ) ;
		Vector visited = new Vector<State> () ;

		boolean found = false ;
		NodeWithState first = new NodeWithState( init ) ;
		if ( init.getHeuristic() == -1 )
		{
			if ( heur == 0 )
				init.misplaced() ;			//	Misplaced Heuristic
			else
				init.evaluate( goal ) ;	//	Manhattan Heuristic
		}

		frontier.add( first ) ;

		int nodesExplored = 0 ;
		int nodesExpanded = 1 ;

		while ( frontier.peek() != null && found == false )
		{
			// pick the head of the queue
			NodeWithState cur = frontier.poll() ;
			State curState = cur.getState() ;
			/*System.out.println ( "Exploring node with (A*) heuristic " +
				( cur.getHeuristic() + cur.getDepth() ) + " heuristic of state " +
				curState.getHeuristic() + " at depth " + cur.getDepth() +
				" with state \n" + curState.toString() ) ;*/
			nodesExplored++ ;

			// check if the state has been visited
			if ( isVisited ( visited , curState ) == false )
			{
				// not visited yet
				visited.addElement( curState ) ;
				if ( curState.equal( goal ) == true )
				{
					//System.out.println( "Path: " + cur.printSolution().toString() ) ;
					//	part of table, change to print
					System.out.println( cur.pathLength() + "\t\t" + nodesExplored +
															"\t\t\t" + nodesExpanded ) ;
					found = true ;
				}
				else
				{
					for ( int i = 1 ; i < 5 ; i++ )
					{
						State n = curState.execution( i ) ;
						if ( n.equal( curState ) == false && isVisited( visited , n ) == false )
						{
							// System.out.println("Adding node with heuristic "+ n.computeHeuristic(goal) +" state \n"+n.toString()) ;
							nodesExpanded++ ;
							if ( heur == 0 )
								n.misplaced() ;				//	Misplaced Heuristic
							else
								n.evaluate( goal ) ;	//	Manhattan Heuristic
							NodeWithState next = new NodeWithState ( n , cur , i ) ;
							frontier.add( next ) ;
						}
					}
				}
			}
		}

		if ( found == false )
			System.out.println( "FALSE - No path from " + init + " to " +  goal ) ;

		//System.out.println( "Explored: " + nodesExplored + " Expanded: " + nodesExpanded ) ;
	}

	public static void main ( String[] args )
	{
		State goal = new State ( 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 0 ) ;

		//	easy
		State init = new State ( 1 , 2 , 3 , 0 , 4 , 5 , 6 , 7 , 8 ) ;

		runTests ( init , goal ) ;
		init = new State ( 0 , 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 ) ;
		runTests ( init , goal ) ;
		init = new State ( 1 , 0 , 2 , 3 , 4 , 5 , 6 , 7 , 8 ) ;
		runTests ( init , goal ) ;
		init = new State ( 0 , 2 , 3 , 1 , 4 , 5 , 6 , 7 , 8 ) ;
		runTests ( init , goal ) ;
	}

	/**
		*	Prints out the results of the searches
		*/
	public static void runTests ( State init , State goal )
	{
		System.out.print( "Method\t\tHeuristic\tPlan length\t#Nodes Explored\t" ) ;
		System.out.println( "\t#Nodes Expanded" ) ;
		//	Set up for Misplaced Heuristic
		System.out.print( "Greedy BFS\tMisplaced\t\t" ) ;
		astar ( init , goal , 1 , 0 ) ;
		//gbfs  ( init , goal , 0 ) ;
		System.out.print( "A-Star\t\tMisplaced\t\t" ) ;
		astar ( init , goal , 0 , 0 ) ;

		//	Set up for Manhattan Heuristic
		System.out.print( "Greedy BFS\tManhatten\t\t" ) ;
		astar ( init , goal , 1 , 1 ) ;
		//gbfs  ( init , goal , 1 ) ;
		System.out.print( "A-Star\t\tManhatten\t\t" ) ;
		astar ( init , goal , 0 , 1 ) ;
		System.out.println() ;
	}
}
