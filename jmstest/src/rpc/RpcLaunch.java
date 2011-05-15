package rpc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.connection.SingleConnectionFactory;

public class RpcLaunch {

	private static Logger logger = LoggerFactory.getLogger(RpcLaunch.class);
	
	private static class RpcTask implements Runnable {

		INameService service;
		int id;
		
		private RpcTask( int i, INameService service ) {
			this.service = service;
			id = i;
		}
		
		@Override
		public void run() {
			try {
				logger.debug( id + " start" );
				service.getName( id );
				logger.debug( id + " end" );
	//			
	//			logger.debug( id + "_lapsed: " + (end.getTime()-start.getTime()));
			}
			catch( RuntimeException e) {
				//e.printStackTrace();
			}
		}
	}
	

	public static void main( String[] args ) throws InterruptedException  {
	
		
    	ApplicationContext context = new FileSystemXmlApplicationContext( new String[] { 
    			"src/resources/spring-rpc.xml"
    	} );
    	
    	int numClients = 2;

    	INameService service1 = (INameService) context.getBean("remoteNameService");

    	List<Thread> threads = new ArrayList<Thread>();
    	
    	for( int i = 0; i < numClients; i++ ) {
	        Thread t = new Thread( new RpcTask( i, service1 ) );
	        t.start();
	        threads.add( t );
    	}

    	for( int i = 0; i < numClients; i++ ) {
	        threads.get(i).join();
    	}

    	SingleConnectionFactory serverFactory = 
    		(SingleConnectionFactory) context.getBean( "serverConnectionFactory" );
    	serverFactory.resetConnection();
    	
    	SingleConnectionFactory clientFactory = 
    		(SingleConnectionFactory) context.getBean( "clientConnectionFactory" );
    	clientFactory.resetConnection();
    	
	}
}
