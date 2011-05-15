package domain
{
	import flash.events.EventDispatcher;

	[Event(name="GlobalInstance", type="domain.SampleEvent")]
	[Event(name="GlobalGlobal", type="domain.SampleEvent")]
	[ManagedEvents(names="GlobalInstance,GlobalGlobal")]
	public class GlobalSender extends EventDispatcher
	{
		public function toInstance(msg:String):void
		{
			dispatchEvent( new SampleEvent( SampleEvent.GLOBAL_INSTANCE, msg ));
		}
		public function toGlobal(msg:String):void
		{
			dispatchEvent( new SampleEvent( SampleEvent.GLOBAL_GLOBAL, msg ));
		}
	}
}