package broker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.jms.ConnectionFactory;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.SimpleMessageListenerContainer;

import pubsub.Consumer;
import pubsub.PublisherSpring;

import common.Listener;


public class PubSubMain {

	static String brokerName = "myBroker";
	static String brokerUrl = "vm://" + brokerName;
	
    public static void main(String[] args) throws Exception {
    	
//    	SimpleMessageListenerContainer c;
//    	org.apache.activemq.broker.BrokerService b;
    	
    	FileSystemXmlApplicationContext context = 
    		new FileSystemXmlApplicationContext( "src/resources/spring-vm.xml" );
    	
    	ConnectionFactory factory = (ConnectionFactory)
    		context.getBean( "jmsConnectionFactory", ConnectionFactory.class );
    	
    	JmsTemplate template = (JmsTemplate)
    		context.getBean( "jmsTemplate", JmsTemplate.class );
    	template.setPubSubDomain( true );

    	ScheduledExecutorService executor = Executors.newScheduledThreadPool(3);
    	
    	int seconds = 4;
    	Topic[] topics = createDestinations();
    	
    	// create 2 publisher
//    	Publisher p1 = new Publisher( brokerUrl, "delete", seconds-1 );
//    	Publisher p2 = new Publisher( brokerUrl, "suspend", seconds-1 );
    	PublisherSpring p1 = new PublisherSpring( "p1", template, topics[0], seconds-1 );
    	PublisherSpring p2 = new PublisherSpring( "p2", template, topics[1], seconds-1 );
    	PublisherSpring p3 = new PublisherSpring( "p3", template, topics[0], seconds-1 );
    	
    	// run publishers
    	executor.submit(p1);
    	executor.submit(p2);
    	executor.submit(p3);
    	
    	// create 3 consumers
    	
//    	Consumer c1 = new Consumer( "c1", factory, new Listener( "c1" ), topics[0] );
    	Consumer c2 = new Consumer( "c2", factory, new Listener( "c2" ), topics[1] );
//    	Consumer c3 = new Consumer( "c3", factory, new Listener( "c3" ), topics );

//    	SimpleMessageListenerContainer c1 = new SimpleMessageListenerContainer();
//    	c1.setConnectionFactory( factory );
//    	c1.setDestination( topics[0] );
//    	c1.setMessageListener( new Listener( "c1" ) );
//    	c1.afterPropertiesSet();
//    	
//    	DefaultMessageListenerContainer c2 = new DefaultMessageListenerContainer();
//    	c2.setConnectionFactory( factory );
//    	c2.setDestination( topics[1] );
//    	c2.setMessageListener( new Listener( "c2" ) );
//    	c2.afterPropertiesSet();
    	
//    	c1.run();
    	c2.run();
//    	c3.run();
    	
    	Thread.currentThread().sleep( seconds * 1000 );
    	
//    	c1.shutdown();
//    	c2.shutdown();
//    	c3.close();
    	executor.shutdown();
    	
    	System.out.println( "all done" );		
    }
    
    public static Topic[] createDestinations() {
    	
    	return new Topic[] {
    			new ActiveMQTopic( "delete" ),
    			new ActiveMQTopic( "suspend" )
    	};
    }
    

}
