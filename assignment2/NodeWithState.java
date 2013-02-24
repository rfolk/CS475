/*
 * Search node class
 *
 */

import java.util.* ;

public class NodeWithState
{

	/**
	 * This class defines the nodes that will be needed in the creation
	 * of the frontier (search tree)
	 */

	private NodeWithState parent ;	// the node that is the parent of this node
	private State state ;						// the state contained in the node
	private int cost ;							// the cost to the node - not used so far
	private int action ;						// the action that was executed to get from
																	// the parent to this node
	private int depth ;							// depth of the node
	private int heuristic ;					// the heuristic value of the state contained
																	// in the node

	// constructor - create a node with null as parent
	public NodeWithState ( State pstate )
	{
		state     = pstate ;
		depth     = 0 ;
		cost      = 0 ;
		heuristic = pstate.getHeuristic() ;
		parent    = null ;
	}

	// constructor - create a node with a parent
	public NodeWithState ( State pstate , NodeWithState pparent , int toAction )
	{
		state     = pstate ;
		action    = toAction ;
		cost      = pparent.getCost() ;
		depth     = pparent.getDepth() + 1 ;
		parent    = pparent ;
		heuristic = pstate.getHeuristic() ;
	}

	// constructor - create a node with null as parent and cost
	public NodeWithState ( State pstate , int cost_to_state , NodeWithState pparent , int toAction )
	{
		state     = pstate ;
		action    = toAction ;
		cost      = pparent.getCost() + cost_to_state ;
		depth     = pparent.getDepth() + 1 ;
		parent    = pparent ;
		heuristic = pstate.getHeuristic() ;
	}

	// method - returns the cost leading to the node
	public int getCost ()
	{
		return cost ;
	}

	// method - returns the cost leading to the node
	public int getDepth ()
	{
		return depth ;
	}

	// method - returns the state of the node
	public State getState ()
	{
		return state ;
	}

	// method - returns the action leading to the node
	public int getAction ()
	{
		return action ;
	}

	// method - returns the parent of the node
	public NodeWithState getParent ()
	{
		return parent ;
	}

	// method - returns the heuristic value of the node
	public int getHeuristic ()
	{
		return heuristic ;
	}

	public void setHeuristic ( int v )
	{
		heuristic = v ;
	}

	// print a solution
	public Vector<State> printSolution ()
	{
		Vector path = new Vector<String> () ;
		NodeWithState cur = this ;
		while ( cur.parent != null )
		{
			State state = cur.state ;
			switch ( cur.action )
			{
				case 1 :
					path.add( 0 , "Left" ) ;
					break ;
				case 2 :
					path.add( 0 , "Right" ) ;
					break ;
				case 3 :
					path.add( 0 , "Up" ) ;
					break ;
				case 4 :
					path.add( 0 , "Down" ) ;
					break ;
			}
			cur = cur.parent ;
		}
		return path ;
	}

	// method - returns a string representation of the node
	public String toString ()
	{
		return "Node : [" + state.toString() + "] [" + cost + "] [" + depth +"]" ;
	}
}
