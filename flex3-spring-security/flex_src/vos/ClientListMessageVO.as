package vos
{
	import mx.collections.ArrayCollection;
	
	[RemoteClass(alias="test.messages.ClientListMessage")]
	public class ClientListMessageVO
	{
		[Bindable]
		public var clientIds:ArrayCollection;
		
		public function ClientListMessageVO()
		{
		}
	}
}
