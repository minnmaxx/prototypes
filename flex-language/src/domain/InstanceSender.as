package domain
{
	import flash.events.EventDispatcher;

	[Event(name="InstanceInstance", type="domain.SampleEvent")]
	[Event(name="InstanceGlobal", type="domain.SampleEvent")]
	[ManagedEvents(names="InstanceInstance,InstanceGlobal")]
	public class InstanceSender extends EventDispatcher
	{
		public function toInstance(msg:String):void
		{
			dispatchEvent( new SampleEvent( SampleEvent.INSTANCE_INSTANCE, msg ));
		}
		public function toGlobal(msg:String):void
		{
			dispatchEvent( new SampleEvent( SampleEvent.INSTANCE_GLOBAL, msg ));
		}
	}
}