package vos
{
	[RemoteClass(alias="test.messages.ChatMessage")]
	public class ChatMessageVO
	{
		[Bindable]
		public var fromId:String;
		
		[Bindable]
		public var toId:String;
		
		[Bindable]
		public var content:String;

		public function ChatMessageVO()
		{
		}
		
		public function toString():String
		{
			if( toId == null ) toId = "all";
			return "(" + fromId + "->" + toId + ") " + content;
		}
	}
}