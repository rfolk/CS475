import java.util.Comparator;

public class StateComparator implements Comparator<NodeWithState> {

    public int compare(NodeWithState x, NodeWithState y)
    {
    	
        if (x.getHeuristic()+x.getDepth() < y.getHeuristic()+y.getDepth())
        {
            return -1;    // x is "better" than y 
        }
        if (x.getHeuristic()+x.getDepth() > y.getHeuristic()+y.getDepth())
        {
            return 1;     // y is "better" than x
        }
        return 0;         // x is "not better nor worse" than y
    }    
}
