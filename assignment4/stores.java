import java.io.* ;
import java.util.* ;
import java.lang.* ;

public class stores
{
	// the set of constraints
	private Vector<Constraint> constraints = new Vector<Constraint> () ;

	// the set of variables
	private Vector<String> variables = new Vector<String> () ;

	// the set of domains
	private Vector<Domain> domains = new Vector<Domain> () ;

	private Vector<Assignment> assignments = new Vector<Assignment> () ;

	public stores ( reader r )
	{
		Iterator<String> itStore = r.getStore () .iterator () ;

		for ( ; itStore.hasNext () ; )
		{
			String item = itStore.next () ;

			if ( item.indexOf ( ":" ) == -1 )
			{
				// read in a constraint
				Constraint c = new Constraint ( item ) ;
				constraints.add ( c ) ;
			}
			else
			{
				// read in a domain
				Domain d = new Domain ( item ) ;
				domains.add ( d ) ;
				variables.add ( d.getName () ) ;
			}
		}
	}

	public stores ( Vector<Constraint> c , Vector<String> v , Vector<Domain> d )
	{
		constraints = c ;
		variables   = v ;
		domains     = d ;
	}

	// return the variables
	public Vector<String> getVariables ()
	{
		return variables ;
	}

	// return the domains
	public Vector<Domain> getDomains ()
	{
		return domains ;
	}

	// return the set of constraints
	public Vector<Constraint> getConstraints ()
	{
		return constraints ;
	}

	// print the CSP problem
	public void printStores ()
	{
		Iterator<Domain> it = domains.iterator () ;

		for ( ; it.hasNext () ; )
		{
			Domain d = it.next () ;
			d.printDomain () ;
		}

		Iterator<Constraint> ic = constraints.iterator () ;

		for ( ; ic.hasNext () ; )
		{
			Constraint c = ic.next () ;
			System.out.println ( c.toString () ) ;
		}

	}

	// checking whether a problem is "well-defined": variables
	// occurring in the constraints should be defined

	public boolean well_defined ()
	{

		Iterator<Constraint> ic = constraints.iterator () ;

		for ( ; ic.hasNext () ; )
		{
			Constraint c  = ic.next () ;
			String first  = c.getFirst () ;
			String second = c.getSecond () ;

			if ( variables.contains ( first ) == false )
				return false ;
			if ( variables.contains ( second ) == false )
				return false ;

		}

		return true ;
	}

	//	make a new assignment to a variable
	public void assign ( Assignment a )
	{
		if ( domains.isEmpty () == true )
			return ;
		assignments.add ( a ) ;
		Iterator<Domain> itrD = domains.iterator () ;
		Domain d = domains.firstElement () ;
		while ( itrD.hasNext () )
		{
			d = itrD.next () ;
			if ( d.getName ().equals ( a.getVariable () ) )
				break ;
		}
		domains.removeElement ( d ) ;
		domains.add ( new Domain ( a.getVariable () , a.getValue () ) ) ;
	}

	//	check if we have a complete solution
	public boolean isComplete ()
	{
		if ( domains.isEmpty () == true )
			return false ;
		Iterator<Domain> itrD = domains.iterator () ;
		while ( itrD.hasNext () )
		{
			Domain d = itrD.next () ;
			if ( d.getDomain ().size () != 1 )
				return false ;
		}
		return true ;
	}

	//	empty the domains for an invalid solution
	public void emptyDomains ()
	{
		domains.clear () ;
	}

	//	AC-3 algorithm
	public boolean ac3 ()
	{
		//if ( domains.isEmpty () )
		//	return false ;
		Queue<Constraint> queue = new LinkedList<Constraint> () ;
		Iterator<Constraint> itrC = constraints.iterator () ;

		//	Add all the arcs to the queue
		while ( itrC.hasNext () )
		{
			Constraint c = itrC.next () ;
			queue.add ( c ) ;
		}
		int i = 1 ;
		while ( !queue.isEmpty() )
		{
			Constraint current = queue.remove() ;
			if ( revised ( current ) == true )
			{
				Vector<Integer> currentD = new Vector<Integer> () ;
				Iterator<Domain> itrD = domains.iterator () ;
				while ( itrD.hasNext () )
				{
					Domain d = itrD.next () ;
					if ( d.getName ().equals ( current.getFirst () ) )
					{
						currentD = d.getDomain () ;
						break ;
					}
				}
				if ( currentD.isEmpty() )
					return false ;
				queue.add ( current ) ;
			}
		}
		return true ;
	}

	//	revised check
	private boolean revised ( Constraint c )
	{
		if ( domains.isEmpty () )
			return false ;
		boolean rev = false ;
		String op = c.getOperation () ;
		Domain d1 = domains.firstElement () ;
		Domain d2 = domains.firstElement () ;
		Iterator<Domain> itrD = domains.iterator () ;
		boolean check ;
		LinkedList<Integer> remove = new LinkedList<Integer> () ;
		while ( itrD.hasNext () )
		{
			Domain d = itrD.next () ;
			if ( d.getName ().equals ( c.getFirst () ) )
				d1 = d ;
			else if ( d.getName ().equals ( c.getSecond () ) )
				d2 = d ;
		}
		domains.removeElement ( d1 ) ;
		d1.printDomain () ;
		d2.printDomain () ;

		Iterator<Integer> itr1 = d1.getDomain ().iterator () ;

		switch ( op )
		{
			case "<=" :
			case "=<" :
				while ( itr1.hasNext () )
				{
					int num1 = itr1.next () ;
					check = false ;
					for ( int i = 0 ; i < d2.getDomain ().size () ; i ++ )
					{
						if ( num1 <= d2.getDomain ().elementAt ( i ) )
							check = true ;
					}
					if ( check == false )
						remove.add ( num1 ) ;
				}
			break ;
			case ">=" :
			case "=>" :
				while ( itr1.hasNext () )
				{
					int num1 = itr1.next () ;
					check = false ;
					for ( int i = 0 ; i < d2.getDomain ().size () ; i ++ )
					{
						if ( num1 >= d2.getDomain ().elementAt ( i ) )
							check = true ;
					}
					if ( check == false )
						remove.add ( num1 ) ;
				}
			break ;
			case "!=" :
				while ( itr1.hasNext () )
				{
					int num1 = itr1.next () ;
					check = false ;
					for ( int i = 0 ; i < d2.getDomain ().size () ; i ++ )
					{
						if ( num1 != d2.getDomain ().elementAt ( i ) )
							check = true ;
					}
					if ( check == false )
						remove.add ( num1 ) ;
				}
			break ;
			case "<" :
				while ( itr1.hasNext () )
				{
					int num1 = itr1.next () ;
					check = false ;
					for ( int i = 0 ; i < d2.getDomain ().size () ; i ++ )
					{
						if ( num1 < d2.getDomain ().elementAt ( i ) )
							check = true ;
					}
					if ( check == false )
						remove.add ( num1 ) ;
				}
			break ;
			case ">" :
				while ( itr1.hasNext () )
				{
					int num1 = itr1.next () ;
					check = false ;
					for ( int i = 0 ; i < d2.getDomain ().size () ; i ++ )
					{
						if ( num1 > d2.getDomain ().elementAt ( i ) )
							check = true ;
					}
					if ( check == false )
						remove.add ( num1 ) ;
				}
			break ;
			case "=" :
				while ( itr1.hasNext () )
				{
					int num1 = itr1.next () ;
					check = false ;
					for ( int i = 0 ; i < d2.getDomain ().size () ; i ++ )
					{
						if ( num1 == d2.getDomain ().elementAt ( i ) )
							check = true ;
					}
					if ( check == false )
						remove.add ( num1 ) ;
				}
			break ;
		}
		if ( !remove.isEmpty () )
		{
			for ( int i = 0 ; i < remove.size () ; i ++ )
				d1.getDomain ().removeElement ( remove.get ( i ) ) ;
			rev = true ;
		}

		domains.add ( d1 ) ;
		return rev ;
	}

}
