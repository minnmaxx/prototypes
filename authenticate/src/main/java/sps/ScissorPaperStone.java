package sps;

import java.util.HashMap;
import java.util.Map;

public class ScissorPaperStone {

	public enum Result {		
		Won, Lost, Tied, NoResult;
		
		public Result opposite()
		{
			switch( this )			
			{
			case Won:
				return Lost;
			case Lost:
				return Won;
			case Tied:
			case NoResult:
				return this;
			}
			throw new IllegalStateException( "Impossible State" );
		}
	}
	
	public enum Selection { 
		Paper, 
		Scissors, 
		Stone;
		
		public Result compare( Selection s )
		{
			if( this == s ) return Result.Tied;
			switch( this )
			{
			case Paper:
				return s == Stone ? Result.Won : Result.Lost;
			case Scissors:
				return s == Paper ? Result.Won : Result.Lost;
			case Stone:
				return s == Scissors ? Result.Won : Result.Lost;
			}
			throw new IllegalStateException( "Impossible State" ); 
		}
	};
	
	private Map<String,Selection> usernameToSelection;
	
	public ScissorPaperStone()
	{
		usernameToSelection = new HashMap<String, Selection>();
	}
	
	public void registerSelection( String username, Selection s )
	{
		usernameToSelection.put( username, s );
	}
	
	public void unregister( String username )
	{
		usernameToSelection.remove( username );
	}
	
	public String getSelectionAsString( String username )
	{
		Selection s = usernameToSelection.get( username );
		if( s == null ) return "NoPick";
		else return s.toString();
	}
	
	/**
	 * return a relationship between player1 and player2
	 * if player1 win, the Result.Won is returned.
	 * @param player1
	 * @param player2
	 * @return
	 */
	public Result getResult( String player1, String player2 )
	{
		Selection s1 = usernameToSelection.get( player1 );
		Selection s2 = usernameToSelection.get( player2 );
		
		if( s1 != null && s2 != null )
		{
			return s1.compare( s2 );
		}
		
		return Result.NoResult;
	}
}

