package test.messages;

public class SubscriptionMessage {

	public static final int TYPE_SUBSCRIBE = 1;
	public static final int TYPE_UNSUBSCRIBE = 2;
	
	public int type;
	
	public SubscriptionMessage() {}
}
