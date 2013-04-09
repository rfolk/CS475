/**
	*	This class allows an assignment to be stored for each variable
	*/

public class Assignment
{
	private String variable ;
	private int    value ;

	/**
		*	Create a new assignment
		*/
	public Assignment ( String var , int val )
	{
		variable = var ;
		value    = val ;
	}

	public String getVariable ()
	{
		return variable ;
	}

	public int getValue ()
	{
		return value ;
	}
}