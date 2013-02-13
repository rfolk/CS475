/*
 * Search node class
 *
 */
public class SearchNode {

	/**
	 * This class defines the nodes that will be needed in the creation
	 * of the frontier (search tree)
	 */

	private SearchNode parent;   // the node that is the parent of this node
	private int state;           // the state contained in the node
	private int cost;            // the cost to the node
	private int action;          // the action that was executed to get from
	                             // the parent to this node
	private int depth;          // the depth of the node

	// constructor - create a node with null as parent

	public SearchNode(int pstate) {
		state  = pstate;
		cost   = 0;
		parent = null;
		depth = 0;
	}

	// constructor - create a node with a parent

	public SearchNode(int pstate, SearchNode pparent) {
		state  = pstate;
		cost   = pparent.getCost();
		parent = pparent;
		depth = pparent.getDepth() + 1;
	}

	// constructor - create a node with parent and cost

	public SearchNode(int pstate, int cost_to_state, SearchNode pparent) {
		state  = pstate;
		cost   = pparent.getCost() + cost_to_state;
		parent = pparent;
		depth = pparent.getDepth() + 1;
	}

	// method - returns the cost leading to the node

	public int getCost() {
		return cost;
	}

	// method - returns the state of the node

	public int getState() {
		return state;
	}

	// method - returns the action leading to the node

	public int getAction() {
		return action;
	}

	// method - returns the depth of  the node

	public int getDepth() {
		return depth;
	}

	// method - returns the parent of the node

	public SearchNode getParent() {
		return parent;
	}

	// method - returns a string representation of the node

    public String toString() {
    	return "Node : [" + state + "] [" + cost + "] [" + depth + "] ";
    }
}