package broker;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.jms.ConnectionFactory;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import pubsub.Consumer;

import common.Listener;

public class StompBroker {

    public static void main(String[] args) throws Exception {
    	
    	try {
			FileSystemXmlApplicationContext context = 
				new FileSystemXmlApplicationContext( "src/resources/spring-stomp.xml" );
			
			ConnectionFactory receiverConnectionFactory = (ConnectionFactory)
				context.getBean( "receiverConnectionFactory", ConnectionFactory.class );
			
			Topic[] topics = createDestinations();
			
			Consumer c1 = new Consumer( "c1", receiverConnectionFactory, new Listener( "c1" ), topics[0] );
			c1.run();
			
			BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
			while( !"q".equals( reader.readLine() ) ) {    		
			}
			reader.close();
			
			System.out.println( "all done" );

    	} catch (Exception e) {
			e.printStackTrace();
		}		
    }
    
    public static Topic[] createDestinations() {
    	
    	return new Topic[] {
    			new ActiveMQTopic( "lse" )
    	};
    }    
}
