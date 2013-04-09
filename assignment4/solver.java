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
		System.out.println ( "Well defined: " + cs.well_defined () + "\n\n" ) ;

		BackTrack ( cs ) ;
		if ( cs.isComplete () == false )
			System.out.println ( "There is no solution!" ) ;
	}

	/**
		*	Back Track Algorithm which implements the AC-3 domain shrinking
		*/
	public static void BackTrack ( stores s )
	{
		//	shrink the domain first to get the smallest domain set to start issuing assignments
		//s.ac3 () ;
		Vector<Constraint> constraints = s.getConstraints () ;
		Vector<String>     variables   = s.getVariables () ;
		Vector<Domain>     domains     = s.getDomains () ;

		Domain d = domains.firstElement () ;

		Iterator<Integer> itrD = d.getDomain ().iterator () ;
		//	to get all solutions, prime the first results with all the possibilities
		while ( itrD.hasNext () )
		{
			int val    = itrD.next () ;
			try
			{
				stores res = backTrackRec ( ( stores ) s.clone () , new Assignment ( d.getName () , val ) ) ;
				if ( res.isComplete () == true )
					printResults ( res ) ;
			}
			catch ( Exception e )
			{
				e.printStackTrace () ;
			}
		}
	}

	/**
		*	BackTrack Recursive Function
		*
		*	Recursively backtrack through the solutions to find an acceptable
		*	solution. This program will return valid solutions or empty solutions.
		*
		*	@param	s	complete CSP state
		*	@param	a	new assignment to make
		*	@return		a completely valid solution or a no solution
		*/
	public static stores backTrackRec ( stores s , Assignment a )
	{
		s.assign ( a ) ;
		//	This is a valid solution, return it
		if ( s.isComplete () == true )
			return s ;
		s.ac3 () ;
		//if ( s.ac3() == true )
		//{
			//	make all the next assignments and go again
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
						try
						{
							return backTrackRec ( ( stores ) s.clone () , new Assignment ( d.getName () , val ) ) ;
						}
						catch ( Exception e )
						{
							e.printStackTrace () ;
						}
					}
				}
			}
		//}
		//	This is not a valid solution
		s.emptyDomains () ;
		return s ;
	}

	/**
		*	printResults
		*	Prints out the results for the CSP
		*/
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
