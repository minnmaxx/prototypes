package org.talz;


privileged public abstract aspect AbstractReadOnlyConfigurableAspect { 
	
	public interface IReadOnlyConfigurable {
		boolean isReadOnly();
	}
	
	public abstract pointcut protectedArea( IReadOnlyConfigurable object );

	void around( IReadOnlyConfigurable object ) : protectedArea( object )  {
			
		if( object.isReadOnly() ) {
//			throw new UnsupportedOperationException( 
//					"Blocked Access at " + thisJoinPointStaticPart.getSignature() );	
		} else {
			proceed( object );
		}
	}	
}
