package org.talz;

import org.aspectj.lang.JoinPoint;
import org.talz.domain.Order;

public aspect Print {

	pointcut allSetFunctions( Object o ) :
		execution( void Order.*(..) ) && 
		!within(Print) && target(o);
	
//	pointcut setIntField( int newVal ) :
//		set( int Pizza.p3 ) && args( newVal );
//	
//	pointcut setIntFieldCf( int arg ) :
//		cflowbelow( setIntField( arg ) ) && !within(Print);
//	
//	pointcut callSetIntField( int arg ) :
//		call( void Pizza.setP3(int) ) && args( arg );
//
//	pointcut callGetIntField() :
//		call( int Pizza.*() );
//		//call( int org.talz..*.*() );
	
//	pointcut callSetIntFieldCf( int arg ) :
//		cflowbelow( callSetIntField( arg ) ) && !within(Print);
	
	before( Object o ) : allSetFunctions( o )  {
		System.out.println( format( "before", o, thisJoinPointStaticPart ) );
	}

	void around( Object o )  : allSetFunctions( o )  {
		
		System.out.println( format( "around enter", o, thisJoinPointStaticPart ) );		
		proceed(o);
		System.out.println( format( "around exit", o, thisJoinPointStaticPart ) );
	}
		
	after( Object o ) : allSetFunctions( o )  {
		System.out.println( format( "after", o, thisJoinPointStaticPart ) );
	}
	
	private String format( String position, Object o, JoinPoint.StaticPart s ) {
		return "[" + Thread.currentThread().getName() + "] " + 
			   position +
			   " [" + o + "] "  + 
			   s;
	}
	
//	
//	after( String p1 ) : allSetFunctions() && args( p1, .. )  {
//		System.out.println( "after " + thisJoinPoint.toLongString() );
//	}
	
//	after() returning( int ret ) : callGetIntField() {
//		System.out.println( "after returning " + thisJoinPoint.toLongString() );
//	}
	
//	pointcut executeSetIntField() :
//		execution( void (Pizza||Order).set*(int) );
	
//	before( int arg ) : setIntFieldCf( arg ) {
//		System.out.println( "before" );
//	}
//
//	after( int arg ) : setIntFieldCf( arg ) {
//		System.out.println( "after" );
//	}	
	
//	int around() : callGetIntField() {
//		int result = proceed();
//		System.out.println( result );
//		return result;
//	}
	
	
	
//	before(int arg) : callSetIntField(arg) {
//		System.out.println( "before " + thisJoinPoint.toLongString() );
//	}
//	before(int arg) : callSetIntFieldCf(arg) {
//		System.out.println( "before " + thisJoinPoint.toLongString() );
//	}	

//	after( int arg ) : callSetIntField( arg ) {
//		System.out.println( "after" );
//	}	
	
//	after( int newVal ): setIntField( newVal ) {
//		System.out.println( "Setting to " + newVal );
//	}
//	
//	before( int arg ) : callSetIntField( arg ) {
//		System.out.println( thisJoinPoint.getClass() );
//		System.out.println( thisJoinPoint.getKind() );
//		System.out.println( thisJoinPoint.getTarget() );
//		System.out.println( thisJoinPoint.getThis() );
//		Signature signature = thisJoinPoint.getSignature();
//		System.out.println( signature.getDeclaringType().getName() );
//		System.out.println( signature.getName() );
//		System.out.println( signature.getModifiers() );
//		
//		System.out.println( thisJoinPoint.toLongString() );
//		System.out.println( thisJoinPoint.getArgs() );
//	}

//	after( int arg ) : callSetIntField( arg ) {
//		System.out.println( "after call " + arg );
//	}
//	
//	before(): executeSetIntField() {
//		System.out.println( "Before execution" );
//	}

//	after(): executeSetIntField() {
//		System.out.println( "after execution" );
//	}

}
