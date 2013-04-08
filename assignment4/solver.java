import java.io.* ;
import java.util.* ;
import java.lang.* ;

public class solver
{

	/**
		* @param args
		*/
	public static void main ( String [] args )
	{
		reader r = new reader ( args [ 0 ] ) ;
		r.printProblem () ;
		stores cs = new stores ( r ) ;
		cs.printStores () ;
		System.out.println ( "Well defined: " + cs.well_defined () ) ;
		//if ( cs.ac3 () == true )
		//	System.out.println ( "There is a solution!" ) ;
		//else

		BackTrack ( cs ) ;
		if ( cs.isComplete () == false )
			System.out.println ( "There is no solution!" ) ;
	}

	public static void BackTrack ( stores s )
	{
		s.ac3 () ;
		Vector<Constraint> constraints = s.getConstraints () ;
		Vector<String>     variables   = s.getVariables () ;
		Vector<Domain>     domains     = s.getDomains () ;

		Domain d = domains.firstElement () ;

		Iterator<Integer> itrD = d.getDomain ().iterator () ;
		while ( itrD.hasNext () )
		{
			int val    = itrD.next () ;
			stores res = backTrackRec ( s , new Assignment ( d.getName () , val ) ) ;
			if ( res.isComplete () == true )
				printResults ( res ) ;
		}
	}

	public static stores backTrackRec ( stores s , Assignment a )
	{
		s.assign ( a ) ;
		if ( s.isComplete () == true )
			return s ;
		Iterator<Domain> itrD = s.getDomains ().iterator () ;
		while ( itrD.hasNext () )
		{
			Domain d = itrD.next () ;
			if ( d.getDomain ().size () > 1 )
			{
				Iterator<Integer> itr2 = d.getDomain ().iterator () ;
				while ( itr2.hasNext () )
				{
					int val = itr2.next () ;
					return backTrackRec ( s , new Assignment ( d.getName () , val ) ) ;
				}
			}
		}
		s.emptyDomains () ;
		return s ;
	}

	public static void printResults ( stores s )
	{
		System.out.println ( "Solution is:" ) ;
		Iterator<Domain> domains = s.getDomains ().iterator () ;
		while ( domains.hasNext () )
		{
			Domain d = domains.next () ;
			System.out.println ( d.getName () + ": " + d.getDomain ().firstElement () ) ;
		}
	}
}
