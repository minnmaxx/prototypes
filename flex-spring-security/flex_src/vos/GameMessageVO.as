package vos
{
	//unless this AS class has matching namespace as java
	//you will need to declare RemoteClass so the serialized object would be mapped to this class instead of a generic object
	// fuck, I forgot "alias" - that's the price of flexibility
	// nah, the IDE does not support good attribute
	[RemoteClass(alias="test.messages.GameMessage")]
	public class GameMessageVO
	{		
		public static const TYPE_ACCEPT_CHALLENGE:int = 1;		
		public static const TYPE_SELECTED_ITEM:int = 2;
		public static const TYPE_RESULT:int = 3;
		public static const TYPE_DECLINE_CHALLENGE:int = 4;
		public static const TYPE_INITIATE_CHALLENGE:int = 5;

		[Bindable]
		public var fromId:String;
		
		[Bindable]
		public var toId:String;
		
		[Bindable]
		public var payLoad:String;
		
		[Bindable]
		public var type:int;

		public function GameMessageVO()
		{
		}
		
		public function toString():String
		{
			return "(" + this.fromId + "," + this.toId + "," + this.type + ") " + this.payLoad;
		}		
	}
}
