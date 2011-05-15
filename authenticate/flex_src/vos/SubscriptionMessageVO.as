package vos
{
	[RemoteClass(alias="sps.messages.SubscriptionMessage")]
	public class SubscriptionMessageVO
	{
		public static const TYPE_SUBSCRIBE:int = 1;		
		public static const TYPE_UNSUBSCRIBE:int = 2;
		
		public var type:int;
		
		public function SubscriptionMessageVO()
		{
		}		
	}
}
