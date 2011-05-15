package org.talz.domain;

import org.talz.AbstractReadOnlyConfigurableAspect;

public aspect ProtectedJoinPointsAspect extends AbstractReadOnlyConfigurableAspect {

	public pointcut protectedArea( IReadOnlyConfigurable object ) : 
		execution( public void org.talz.domain..*.set*( .. ) ) && this( object );	
}


