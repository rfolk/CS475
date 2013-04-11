import java.io.* ;
import java.util.* ;
import java.lang.* ;

public class Domain
{
	// variable name
	private String name ;
	// set of values of the variable
	private Vector<Integer> domain = new Vector<Integer> () ;
	// create a domain of a variable from a string
	// x : [1,3,4,....]
	public Domain ( String s )
	{
		StringTokenizer tok = new StringTokenizer ( s, " :[]" ) ;

		name = tok.nextToken () ;

		String tmp = tok.nextToken () ;

		StringTokenizer tokv = new StringTokenizer ( tmp, "," ) ;

		while ( tokv.hasMoreElements () )
		{
			String t = tokv.nextToken () ;
			domain.add ( Integer.parseInt ( t ) ) ;
		}

	}
	public Domain ( String n , int d )
	{
		name = n ;
		domain.add ( d ) ;
		System.out.println ( domain.size () ) ;
	}

	// return the variable name
	public String getName ()
	{
		return name ;
	}

	// return the set of values ( domain )
	public Vector<Integer> getDomain ()
	{
		return domain ;
	}

	public void printDomain ()
	{
		System.out.println ( "Variable name: " + name ) ;
		System.out.print ( "Variable domain: " ) ;
		Iterator<Integer> it = domain.iterator () ;

		for ( ; it.hasNext () ; )
		{
			Integer value = it.next () ;
			System.out.print ( value + " " ) ;
		}

		System.out.println () ;
	}

	public String toString ()
	{
		return name + domain.toString () ;
	}

}
