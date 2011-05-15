package pubsub;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import javax.jms.Destination;
import javax.jms.JMSException;

import org.apache.activemq.broker.BrokerService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;

public class PublisherSpring implements Runnable {
	
	private static final Log LOG = LogFactory.getLog(PublisherSpring.class);

	private JmsTemplate template;
	private Destination dest;
	private int msgTotal;
	private String id;
	private AtomicInteger msgId = new AtomicInteger( 100000 );
	
	public PublisherSpring( String id, JmsTemplate template, Destination dest, int total ) {
		this.id = id;
		this.template = template;
		this.dest = dest;
		this.msgTotal = total;
	}

	public void run() {
		
        try {
			
    		for (int i = 0; i < msgTotal; i++) {
		    	sendMessage();
		    	Thread.sleep(1000);
		    }
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
	    }
	}
	
    protected void sendMessage() throws JMSException {
    	 
    	template.convertAndSend( dest, "Publisher[" + id + "] " + Integer.toString( msgId.incrementAndGet() ) );

//    	LOG.info( "Publisher[" + id + "] created message: [dest=" + dest.toString() 
//        		+ ",msg-id=" + Integer.toString( msgId.get() ) + "]" );
    	
//        template.send( dest, new MessageCreator() {
//
//			public Message createMessage(Session session) throws JMSException {
//				
//				Message message = session.createObjectMessage(msgId++);
//				
//				
//				return message;
//			}
//        });
    }
}
