package rpc;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.connection.SingleConnectionFactory;

public class RpcClient {

	private static class RpcTask implements Runnable {

		INameService service;
		int seconds;
		
		private RpcTask( int i, INameService service ) {
			this.service = service;
			seconds = i;
		}
		
		@Override
		public void run() {
			Date start = new Date();
			String result = service.getName( seconds );
			Date end = new Date();
			System.out.println( seconds + "_start: " + start.getTime() );
			System.out.println( seconds + "_end: " + end.getTime() );
			System.out.println( seconds + "_lapsed: " + (end.getTime()-start.getTime()));
		}
	}

    public static void main(String[] args) throws Exception {
    	
    	ApplicationContext ctx = new FileSystemXmlApplicationContext( new String[] { 
    			"src/resources/spring-rpc-client.xml",
    			"src/resources/spring-rpc-share.xml"
    	} );

    	INameService service1 = (INameService) ctx.getBean("service1");
    	INameService service2 = (INameService) ctx.getBean("service2");
        
        Thread t1 = new Thread( new RpcTask( 4, service1 ) );
        Thread t4 = new Thread( new RpcTask( 4, service2 ) );

        t4.start();
        t1.start();
        
        t1.join();        
        t4.join();        
        
        SingleConnectionFactory connectionFactory = (SingleConnectionFactory) ctx.getBean( "jmsConnectionFactory" );
        connectionFactory.resetConnection();
    }
}
