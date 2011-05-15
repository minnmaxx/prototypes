package common;


import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pubsub.PublisherSpring;

public class Listener implements MessageListener {

//	private String job;
//	
//	public Listener(String job) {
//		this.job = job;
//	}
	
	private static final Log LOG = LogFactory.getLog(Listener.class);
	
	public String consumerId;
	
	public Listener() {
		
	}
	
	public Listener(String id) {
		consumerId = id;
	}

	public void onMessage(Message message) {
		try {
			Destination dest = message.getJMSDestination();
			
			//do something here
			System.out.println( "Consumer[" + consumerId + "] received message: " 
					+ "[dest=" + dest.toString() 
					+ "," + ((TextMessage)message).getText() + "]" );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
