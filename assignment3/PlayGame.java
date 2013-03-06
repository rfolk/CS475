import java.util.* ;
import java.io.* ;
import java.lang.* ;

public class PlayGame
{
	public static int nodes ;
	/**
		*	print the possible actions
		*/
	public static void printActions ( Vector v )
	{
		Iterator< int [] > itSucc = v.iterator () ;

		for ( ; itSucc.hasNext () ; )
		{
			int [] pair = itSucc.next () ;
			System.out.println ( pair [ 0 ] + ", " + pair [ 1 ] ) ;
		}
	}

	/**
		*	testPlay: this test the actions, the
		*/
	public static void testPlay ()
	{
		State board = new State () ;

		Vector actions = new Vector< int [] > () ;

		System.out.println ( board.toString () ) ;

		while ( board.terminal () == false )
		{
			System.out.println ( board.toString () ) ;
			System.out.println ( "Player " + board.getPlayer () ) ;
			actions = board.action () ;
			Iterator< int [] > itSucc = actions.iterator () ;
			printActions ( actions ) ;
			int [] a = new int [ 2 ] ;
			int player = board.getPlayer () ;
			a = itSucc.next () ;
			// play the first available move
			State c = board.result ( player , a [ 0 ] , a [ 1 ] ) ;
			board = c ;
		}
		System.out.println ( "Final? " + board.terminal () ) ;
		System.out.println ( "Value? " + board.utility () ) ;
	}

	public static void testPlayOptimal ()
	{
		//	State  board = new State();

		//State board = new State ( 2 , 1 , 0 , 0 , 1 , 0 , 0 , 2 , 0 ) ;
		nodes = 0 ;
		State board = new State () ;

		while ( board.terminal () == false )
		{
			int [] move = minimax ( board ) ;
			System.out.println ( "Player: " + board.getPlayer () +
													 "\nMove: (" + move [ 0 ] + "," + move [ 1 ] +
													 ") => value " + move [ 2 ] ) ;
			State c = board.result ( board.getPlayer () , move [ 0 ] , move [ 1 ] ) ;
			board = c.copy () ;
			System.out.println ( "Board \n" + board.toString () ) ;
		}
		System.out.println ( "Final? " + board.terminal () ) ;
		System.out.println ( "Value? " + board.utility () ) ;
		System.out.println ( "Nodes created: " + nodes ) ;

		System.out.println ( "\n\n\n\n" ) ;

		nodes = 0 ;
		board = new State () ;
		board = new State ( 2 , 1 , 0 , 0 , 1 , 0 , 0 , 2 , 0 ) ;

		while ( board.terminal () == false )
		{
			int [] move = alphabeta ( board ) ;
			System.out.println ( "Player: " + board.getPlayer () +
													 "\nMove: (" + move [ 0 ] + "," + move [ 1 ] +
													 ") => value " + move [ 2 ] ) ;
			State c = board.result ( board.getPlayer () , move [ 0 ] , move [ 1 ] ) ;
			board = c.copy () ;
			System.out.println ( "Board \n" + board.toString () ) ;
		}
		System.out.println ( "Final? " + board.terminal () ) ;
		System.out.println ( "Value? " + board.utility () ) ;
		System.out.println ( "Nodes created: " + nodes ) ;


	}

	/**
		*	testAction used for testing the computation of
		*	possible actions in a given configuration
		*/
	public static void testAction ()
	{
		State board = new State () ;

		Vector actions = new Vector<int[]> () ;

		System.out.println ( board.toString () ) ;
		actions = board.action () ;
		printActions ( actions ) ;

		System.out.println ( "After setting 1 at 0 0" ) ;
		board.setAt ( 1 , 0 , 0 ) ;
		actions = board.action () ;
		printActions ( actions ) ;
	}

	/**
		*	Test the utility function
		*/
	public static void testUtility ()
	{
		State board = new State ( 1 , 1 , 1 , 2 , 2 , 0 , 0 , 0 , 0 ) ;

		System.out.println ( board.toString () ) ;
		System.out.println ( "Final? " + board.terminal () ) ;
		System.out.println ( "Value? " + board.utility () ) ;

		board = new State ( 1 , 1 , 0 , 2 , 2 , 2 , 1 , 0 , 0 ) ;
		System.out.println ( board.toString () ) ;
		System.out.println ( "Final? " + board.terminal () ) ;
		System.out.println ( "Value? " + board.utility () ) ;

		board = new State ( 1 , 1 , 2 , 2 , 2 , 2 , 1 , 1 , 0 ) ;
		System.out.println ( board.toString () ) ;
		System.out.println ( "Final? " + board.terminal () ) ;
		System.out.println ( "Value? " + board.utility () ) ;

		board = new State ( 1 , 1 , 2 , 1 , 2 , 1 , 2 , 1 , 2 ) ;
		System.out.println ( board.toString () ) ;
		System.out.println ( "Final? " + board.terminal () ) ;
		System.out.println ( "Value? " + board.utility () ) ;
	}

	/**
		*	Minimax
		*
		*	This generates a gametree that calculates every possible move recursively
		*	to discover the move which is most beneficial to make. This will block a
		*	move that exists and allows the opposing player to win the game as well
		*	as win the game if a game winning move exists.
		*
		*	@param	s		state		the current state of the board
		*	@return							returns the result of the serach.
		*											move[0]: the row to make the move
		*											move[1]: the column to make the move
		*											move[2]: the utility cost of the move
		*/
	public static int [] minimax ( State s )
	{
		Vector< int [] > actions = s.action () ;
		int [] result = new int [ 3 ] ;
		//	start the result at "negative infinity"
		result [ 2 ]  = Integer.MIN_VALUE ;
		for ( int i = 0 ; i < actions.size () ; i ++ )
		{
			nodes ++ ;	//	track nodes created, used for testing
			int [] move = actions.elementAt ( i ) ;
			int [] curr = minvalue ( s.result (
															 s.getPlayer () , move [ 0 ] , move [ 1 ] ) ) ;
			if ( curr [ 2 ] > result [ 2 ] )
			{
				result [ 0 ] = move [ 0 ] ;
				result [ 1 ] = move [ 1 ] ;
				result [ 2 ] = curr [ 2 ] ;
			}
		}
		return result ;
	}

	/**
		*	MinValue
		*
		*	Recursively finds the minimum maximum value from available actions.
		*
		*	@param	s			state				the current state of the board
		*	@return										the utility score of the best move
		*/
	public static int [] minvalue ( State s )
	{
		if ( s.terminal () == true )
		{
			//	if player 1, negate the result to get the MIN perspective
			if ( s.getPlayer () == 1 )
				return new int []{ -1 , -1 , -s.utility () } ;
			return new int [] { -1 , -1 , s.utility () } ;
		}

		Vector< int [] > actions = s.action () ;
		//	start score at "possitive infinity"
		int [] move   = new int [ 2 ] ;
		int [] result = new int [ 3 ] ;
		result [ 2 ]  = Integer.MAX_VALUE ;
		for ( int i = 0 ; i < actions.size () ; i ++ )
		{
			nodes ++ ;	//	track nodes created, used for testing
			move        = actions.elementAt ( i ) ;
			int [] curr = maxvalue ( s.result (
															 s.getPlayer () , move [ 0 ] , move [ 1 ] ) ) ;
			if ( curr [ 2 ] < result [ 2 ] )
			{
				result [ 0 ] = move [ 0 ] ;
				result [ 1 ] = move [ 1 ] ;
				result [ 2 ] = curr [ 2 ] ;
			}
		}
		return result ;
	}

	/**
		*	MaxValue
		*
		*	Recursively finds the maximum minimum value from available actions.
		*
		*	@param	s			state				the current state of the board
		*	@return										the utility score of the best move
		*/
	public static int [] maxvalue ( State s )
	{
		if ( s.terminal () == true )
		{
			if ( s.getPlayer () == 1 )
				return new int [] { -1 , -1 , s.utility () } ;
			//	if player 2, negate the result to get MIN perspective
			return new int [] { -1 , -1 , -s.utility () } ;
		}

		Vector< int [] > actions = s.action () ;
		//	start score at "negative infinity"
		int [] move   = new int [ 2 ] ;
		int [] result = new int [ 3 ] ;
		result [ 2 ]  = Integer.MIN_VALUE ;
		for ( int i = 0 ; i < actions.size () ; i ++ )
		{
			nodes ++ ;	//	track nodes created, used for testing
			move        = actions.elementAt ( i ) ;
			int [] curr = minvalue ( s.result (
														s.getPlayer () , move [ 0 ] , move [ 1 ] ) ) ;
			if ( curr [ 2 ] > result [ 2 ] )
			{
				result [ 0 ] = move [ 0 ] ;
				result [ 1 ] = move [ 1 ] ;
				result [ 2 ] = curr [ 2 ] ;
			}
		}
		return result ;
	}


	/**
		*	Alpha-Beta Pruning
		*
		*	This generates a gametree that prunes the results as soon as it has found
		*	a minimum that is lower than another branch of the subtree.
		*
		*	@param	s		state		the current state of the board
		*	@return							returns the result of the serach.
		*											move[0]: the row to make the move
		*											move[1]: the column to make the move
		*											move[2]: the utility cost of the move
		*/
	public static int [] alphabeta ( State s )
	{
		Vector< int [] > actions = s.action () ;
		int [] result = new int [ 3 ] ;
		//	start score at "negative infinity"
		result [ 2 ]  = Integer.MIN_VALUE ;
		for ( int i = 0 ; i < actions.size () ; i ++ )
		{
			nodes ++ ;	//	track nodes created, used for testing
			int [] move = actions.elementAt ( i ) ;
			int [] curr = abMinValue ( s.result (
																 s.getPlayer () , move [ 0 ] , move [ 1 ] ) ,
															 	 Integer.MIN_VALUE , Integer.MAX_VALUE ) ;
			if ( curr [ 2 ] > result [ 2 ] )
			{
				result [ 0 ] = move [ 0 ] ;
				result [ 1 ] = move [ 1 ] ;
				result [ 2 ] = curr [ 2 ] ;
			}
		}
		return result ;
	}

	/**
		*	Alpha-Beta MaxValue
		*
		*	Recursively finds the maximum minimum value from available actions.
		*
		*	@param s			state				the current state of the board
		*	@param a			alpha				the maximum value found in the chains
		* @param b 			beta				the minimum value found in the chains
		*	@return										the utility score of the best move
		*/
	public static int [] abMaxValue ( State s , int a , int b )
	{
		if ( s.terminal () == true )
		{
			if ( s.getPlayer () == 1 )
				return new int [] { -1 , -1 , s.utility () } ;
			//	if player 2, negate the result to get MIN perspective
			return new int [] { -1 , -1 , -s.utility () } ;
		}

		Vector< int [] > actions = s.action () ;
		//	start score at "negative infinity"
		int [] move   = new int [ 2 ] ;
		int [] result = new int [ 3 ] ;
		result [ 2 ]  = Integer.MIN_VALUE ;
		for ( int i = 0 ; i < actions.size () ; i ++ )
		{
			nodes ++ ;	//	track nodes created, used for testing
			move        = actions.elementAt ( i ) ;
			int [] curr = abMinValue ( s.result (
																 s.getPlayer () , move [ 0 ] , move [ 1 ] ) , a , b ) ;
			if ( curr [ 2 ] > result [ 2 ] || curr [ 2 ] >= b )
			{
				result [ 0 ] = move [ 0 ] ;
				result [ 1 ] = move [ 1 ] ;
				result [ 2 ] = curr [ 2 ] ;
				if ( curr [ 2 ] >= b )
					return result ;
			}
			a = Math.max ( a , curr [ 2 ] ) ;
		}
		return result ;
	}

	/**
		*	Alpha-Beta MinValue
		*
		*	Recursively finds the minimum maximum value from available actions.
		*
		*	@param s			state				the current state of the board
		*	@param a			alpha				the maximum value found in the chains
		* @param b 			beta				the minimum value found in the chains
		*	@return										the utility score of the best move
		*/
	public static int [] abMinValue ( State s , int a , int b )
	{
		if ( s.terminal () == true )
		{
			//	if player 1, negate the result to get the MIN perspective
			if ( s.getPlayer () == 1 )
				return new int [] { -1 , -1 , -s.utility () } ;
			return new int [] { -1 , -1 , s.utility () } ;
		}

		Vector< int [] > actions = s.action () ;
		//	start score at "possitive infinity"
		int [] move   = new int [ 2 ] ;
		int [] result = new int [ 3 ] ;
		result [ 2 ]  = Integer.MAX_VALUE ;
		for ( int i = 0 ; i < actions.size () ; i ++ )
		{
			nodes ++ ;	//	track nodes created, used for testing
			move        = actions.elementAt ( i ) ;
			int [] curr = abMaxValue ( s.result (
																 s.getPlayer () , move [ 0 ] , move [ 1 ] ) , a , b ) ;
			if ( curr [ 2 ] < result [ 2 ] || curr [ 2 ] <= a )
			{
				result [ 0 ] = move [ 0 ] ;
				result [ 1 ] = move [ 1 ] ;
				result [ 2 ] = curr [ 2 ] ;
				if ( curr [ 2 ] >= b )
					return result ;
			}
			b = Math.min ( b , curr [ 2 ] ) ;
		}
		return result ;
	}

	/**
	 * @param args
	 */
	public static void main ( String [] args )
	{
		//testAction();
		//testUtility();
		testPlayOptimal();

		/* it is expected that the following should be able to executed

		State board = new State();
		int[] move = minimax(board);
		System.out.println("Move: ("+move[0]+","+move[1]+") => value " + move[2]);*/


		/*State board = new State(2,1,1,0,1,0,0,2,0);
		board.setPlayer(2);
		int[] move = minvalue(board);
		System.out.println(board.toString());
		System.out.println("Move: ("+move[0]+","+move[1]+") => value " + move[2]);
		board = board.result(board.getPlayer(),move[0],move[1]) ;
		System.out.println(board.toString());*/

	}
}
