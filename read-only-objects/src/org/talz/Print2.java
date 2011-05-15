package org.talz;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.talz.domain.Order;


@Aspect
public class Print2 {

	@Pointcut("execution( * org.talz.domain.Order.*(..) ) && target(o)")
	public void logPoint( Order o ) {}

	@Before("logPoint(o)")
	public void log( Order o, JoinPoint.StaticPart thisJoinPointStaticPart ) {
		System.out.println( "log before " + thisJoinPointStaticPart );
	}
}
