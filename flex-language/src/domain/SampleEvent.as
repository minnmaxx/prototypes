package domain
{
	import flash.events.Event;

	public class SampleEvent extends Event
	{
		public static const INSTANCE_GLOBAL:String = "InstanceGlobal";
		public static const INSTANCE_INSTANCE:String = "Instance";
		public static const GLOBAL_INSTANCE:String = "GlobalInstance";
		public static const GLOBAL_GLOBAL:String = "GlobalGlobal";
		
		public var message:String;
		
		public function SampleEvent(type:String, message:String)
		{
			super( type );
			this.message = message;
		}
	}
}