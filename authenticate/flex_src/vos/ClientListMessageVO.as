package vos
{
	import mx.collections.ArrayCollection;
	
	[RemoteClass(alias="sps.messages.ClientListMessage")]
	public class ClientListMessageVO
	{
		[Bindable]
		public var clientIds:ArrayCollection;
		
		public function ClientListMessageVO()
		{
		}
	}
}
