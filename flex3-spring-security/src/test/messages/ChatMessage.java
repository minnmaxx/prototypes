package test.messages;

public class ChatMessage {

	public String fromId;
	public String toId;
	public String content;
	
	// empty constructor needed for serialization
	public ChatMessage() {}

	public ChatMessage( String f, String t )
	{
		this( f, t, null );
	}
	
	public ChatMessage( String f, String t, String c )
	{
		fromId = f;
		toId = t;
		content = c;
	}
}
