public class Assignment
{
	private String variable ;
	private int    value ;

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