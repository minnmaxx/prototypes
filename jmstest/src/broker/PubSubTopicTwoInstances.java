package broker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.jms.ConnectionFactory;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import pubsub.Consumer;
import pubsub.PublisherSpring;

import common.Listener;

public class PubSubTopicTwoInstances {

	
    public static void main(String[] args) throws Exception {
    	
    	FileSystemXmlApplicationContext context = 
    		new FileSystemXmlApplicationContext( "src/resources/spring-pubsub.xml" );
    	
    	ConnectionFactory receiverConnectionFactory = (ConnectionFactory)
    		context.getBean( "serverConnectionFactory", ConnectionFactory.class );
    	
    	JmsTemplate senderTemplate = (JmsTemplate)
    		context.getBean( "clientJmsTemplate", JmsTemplate.class );

    	ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
    	
    	int seconds = 4;
    	Topic[] topics = createDestinations();
    	
    	Consumer c1 = new Consumer( "c1", receiverConnectionFactory, new Listener( "c1" ), topics[0] );
    	c1.run();
    	
    	Thread.sleep( 3000 );
    	
    	// create 2 publisher
    	PublisherSpring p1 = new PublisherSpring( "p1", senderTemplate, topics[0], seconds-1 );
    	//PublisherSpring p2 = new PublisherSpring( "p2", template, topics[1], seconds-1 );
    	
    	// run publishers
    	executor.submit(p1);
    	//executor.submit(p2);
    	
    	// create 3 consumers
    	
    	
    	Consumer c2 = new Consumer( "c2", receiverConnectionFactory, new Listener( "c2" ), topics[1] );
//    	Consumer c3 = new Consumer( "c3", receiverConnectionFactory, new Listener( "c3" ), topics );
    	
//    	c1.run();
    	c2.run();
//    	c3.run();
    	
    	Thread.sleep( seconds * 1000 );
    	
    	executor.shutdown();
    	
    	System.out.println( "all done" );		
    }
    
    public static Topic[] createDestinations() {
    	
    	return new Topic[] {
    			new ActiveMQTopic( "delete" ),
    			new ActiveMQTopic( "delete" )
    	};
    }
    	
}
