package domain
{
	public class InitObject
	{
		public function InitObject()
		{
		}
		
		[Inject]
		public var another:AnotherObject;
		
		[Init]
		public function init():void {
		}
		
		public function sum():int {
			trace( "bomb" );
			return another.sum();
		}
	}
}