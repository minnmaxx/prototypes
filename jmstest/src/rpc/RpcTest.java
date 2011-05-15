package rpc;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

@ContextConfiguration(locations={
		"classpath:/resources/spring-rpc.xml"
})
public class RpcTest extends AbstractTestNGSpringContextTests {
	
	private static Logger logger = LoggerFactory.getLogger(RpcTest.class);	

	private AtomicInteger id = new AtomicInteger(0);
	
	private boolean[] checked = new boolean[5];
	
	@Resource(name="remoteNameService")
	private INameService service;
	
	@Test(invocationCount=5,threadPoolSize=5)
	public void startClient() {
		
		int clientId = id.getAndIncrement(); 
		service.getName( clientId );
		
		checked[clientId] = true;
	
		logger.debug( "{} ended", clientId );
	}
	
	@Test(dependsOnMethods={"startClient"})
	public void check() {
		
		logger.debug( "start" );
		
		for( int i = 0; i < 5; i++ ) {
			Assert.assertTrue( checked[i] );
		}
	}
}
