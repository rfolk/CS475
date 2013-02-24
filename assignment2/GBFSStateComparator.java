import java.util.Comparator ;

/**
	*	State Comparator for the Greedy Best First Search
	*
	*	This is a modified version of the StateComparator given that merely relies
	*	on the Heuristic value of an operation as is required by the Greedy BFS.
	*/
public class GBFSStateComparator implements Comparator<NodeWithState>
{
	public int compare(NodeWithState x, NodeWithState y)
	{
		if ( x.getHeuristic() < y.getHeuristic() )
			return -1 ;			// x is "better" than y

		if ( x.getHeuristic() > y.getHeuristic() )
			return 1 ;			// y is "better" than x

		return 0 ;				// x is "not better nor worse" than y
	}
}
