
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
	}

}
