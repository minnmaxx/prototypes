package sps.messages;

public class GameMessage {
	
	public static final int TYPE_ACCEPT_CHALLENGE = 1;
	public static final int TYPE_SELECTED_ITEM = 2;
	public static final int TYPE_RESULT = 3;
	public static final int TYPE_DECLINE_CHALLENGE = 4;
	public static final int TYPE_INITIATE_CHALLENGE = 5;
	
	public String fromId;
	public String toId;
	public String payLoad;
	public int type;
	
	// empty constructor needed for serialization
	public GameMessage() {}

	public GameMessage( String from, String to, String payLoad, int type )
	{
		this.fromId = from;
		this.toId = to;
		this.payLoad = payLoad;
		this.type = type;
	}
	
	@Override
	public String toString()
	{
		return "(" + this.fromId + "," + this.toId + "," + this.type + ") " + this.payLoad;
	}
}
