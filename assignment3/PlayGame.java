import java.util.*;
import java.io.*;
import java.lang.*;

public class PlayGame
{

	/**
		*	print the possible actions
		*/
	public static void printActions ( Vector v )
	{
		Iterator< int[] > itSucc = v.iterator () ;

		for ( ; itSucc.hasNext () ; )
		{
			int [] pair = itSucc.next() ;
			System.out.println ( pair [ 0 ] + ", " + pair [ 1 ] ) ;
		}
	}

	/**
		*	testPlay: this test the actions, the
		*/
	public static void testPlay ()
	{
		State board = new State () ;

		Vector actions = new Vector< int[] > () ;

		System.out.println ( board.toString() ) ;

		while ( board.terminal() == false )
		{
			System.out.println ( board.toString () ) ;
			System.out.println ( "Player " + board.getPlayer () ) ;
			actions = board.action() ;
			Iterator< int[] > itSucc = actions.iterator () ;
			printActions( actions ) ;
			int[] a = new int [ 2 ] ;
			int player = board.getPlayer () ;
			a = itSucc.next () ;
			// play the first available move
			State c = board.result ( player , a[ 0 ] , a[ 1 ] ) ;
			board = c ;
		}
		System.out.println ( "Final ?" + board.terminal () ) ;
		System.out.println ( "Value ?" + board.utility () ) ;
	}

	public static void testPlayOptimal ()
	{
		//	State  board = new State();

		State board = new State ( 2 , 1 , 0 , 0 , 1 , 0 , 0 , 2 , 0 ) ;

		while ( board.terminal () == false )
		{
			int[] move = minimax ( board ) ;
			System.out.println ( "Player: " + board.getPlayer () +
													 "\nMove: [" + move [ 0 ] + "," + move [ 1 ] +
													 "] => value " + move [ 2 ] ) ;
			State c = board.result ( board.getPlayer () , move [ 0 ] , move [ 1 ] ) ;
			board = c.copy () ;
			System.out.println ( "Board \n" + board.toString () ) ;
		}
		System.out.println ( "Final ?" + board.terminal () ) ;
		System.out.println ( "Value ?" + board.utility () ) ;
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
		System.out.println ( "Final ?" + board.terminal () ) ;
		System.out.println ( "Value ?" + board.utility () ) ;

		board = new State ( 1 , 1 , 0 , 2 , 2 , 2 , 1 , 0 , 0 ) ;
		System.out.println ( board.toString () ) ;
		System.out.println ( "Final ?" + board.terminal () ) ;
		System.out.println ( "Value ?" + board.utility () ) ;

		board = new State ( 1 , 1 , 2 , 2 , 2 , 2 , 1 , 1 , 0 ) ;
		System.out.println ( board.toString () ) ;
		System.out.println ( "Final ?" + board.terminal () ) ;
		System.out.println ( "Value ?" + board.utility () ) ;

		board = new State ( 1 , 1 , 2 , 1 , 2 , 1 , 2 , 1 , 2 ) ;
		System.out.println ( board.toString () ) ;
		System.out.println ( "Final ?" + board.terminal () ) ;
		System.out.println ( "Value ?" + board.utility () ) ;
	}

	/*
			Implement the minimax or the alpha-beta search algorithms
		*/

	/*
			you should implement the algorithm that will return
			the move that the player who has the turn should
			play - look for the description of the alpha-beta-algorithm
			implementation in the book of note
			int[] is only the place holder - you can change it into
			anything you like
		*/
	public static int [] minimax ( State s )
	{

	}

	/*
			you should implement the minvalue function
			or minvalue for alpha-beta search  - again,
			change int[] to anything that you feel it should be
		*/
	public static int [] minvalue ( State s )
	{

	}

	/*
			you should implement the maxvalue function
			or maxvalue for alpha-beta search  - again,
			change int[] to anything that you feel it should be
		*/
	public static int [] maxvalue ( State s )
	{

	}

	/**
	 * @param args
	 */
	public static void main ( String [] args )
	{
		testAction();
		testUtility();
		testPlay();

		/* it is expected that the following should be able to executed

		State board = new State();
		int[] move = minimax(board);
		System.out.println("Move: ["+move[0]+","+move[1]+"] => value " + move[2]);


		State board = new State(2,1,1,0,1,0,0,2,0);
		board.setPlayer(2);
		int[] move = minvalue(board);
		System.out.println(board.toString());
		System.out.println("Move: ["+move[0]+","+move[1]+"] => value " + move[2]);
		*/
	}
}
