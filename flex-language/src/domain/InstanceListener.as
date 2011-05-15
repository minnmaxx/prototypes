package domain
{
	[Bindable]
	public class InstanceListener
	{
		public var result:String;
		
		public function InstanceListener()
		{
		}
		
		[MessageHandler(selector="InstanceInstance")]
		public function handleInstance( event:SampleEvent ):void {
			result = "Instance-" + event.message;	
		}
		
		[MessageHandler(selector="GlobalInstance")]
		public function handleGlobal( event:SampleEvent ):void {
			result = "Global-" + event.message;	
		}
	}
}