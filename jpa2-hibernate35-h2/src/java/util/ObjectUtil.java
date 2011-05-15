package util;

import java.util.Calendar;

public class ObjectUtil {

	public static void checkInputNotNull( Object o ) {
		checkNotNull(o,"One of the arguments is null");
	}
	public static void checkRepositoryObjectNotNull( Object o ) {
		checkNotNull(o,"Object not found in respository");
	}	
	
	public static void checkNotNull( Object o, String message ) {
		if( o == null )
		{
			throw new IllegalArgumentException( message );			
		}		
	}
	
	public static Calendar clone( Calendar object ) {
		if( object == null ) {
			return null;
		}
		return (Calendar) object.clone();
	}
}
