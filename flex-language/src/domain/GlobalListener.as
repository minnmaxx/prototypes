package domain
{
	[Bindable]
	public class GlobalListener
	{
		public var result:String;
		
		public function GlobalListener()
		{
		}
		
		[MessageHandler(selector="InstanceGlobal")]
		public function handleInstance( event:SampleEvent ):void {
			result = "Instance-" + event.message;	
		}
		
		[MessageHandler(selector="GlobalGlobal")]
		public function handleGlobal( event:SampleEvent ):void {
			result = "Global-" + event.message;	
		}		
	}
}