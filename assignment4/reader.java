import java.io.* ;
import java.util.* ;
import java.lang.* ;

public class reader
{

	private Vector stores = new Vector<String> () ;

	public reader ( String fname )
	{

		Scanner inFile = null ;

		try
		{
			inFile = new Scanner ( new File ( fname ) ) ;
			String s ;
			while ( inFile.hasNextLine () )
			{
				s = inFile.nextLine () ;
				stores.addElement ( s ) ;
			}
		}
		catch ( IOException e )
		{
			// If another exception is generated, print a stack trace
			e.printStackTrace () ;
		}
		finally
		{
			if ( inFile != null )
				inFile.close () ;
		}

	}

	public Vector<String> getStore ()
	{
		return stores ;
	}

	public void printProblem ()
	{

			Iterator<String> itStore = stores.iterator () ;

			for ( ; itStore.hasNext () ; )
			{
				String item = itStore.next () ;
				System.out.println ( item ) ;
			}

	}

}
