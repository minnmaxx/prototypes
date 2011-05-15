package common;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;

public class AbstractConsumer {

    private Connection connection;
    private Session session;
    private Listener listener;
    private Topic[] topics;
    
    public AbstractConsumer(String id, ConnectionFactory factory, Topic[] topics, Listener listener) throws JMSException {
    	connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        this.topics = topics;
        this.listener = listener;
        this.listener.consumerId = id;
    }
    
    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }    
    
//    public static void main(String[] args) throws JMSException {
//    	Consumer consumer = new Consumer( "c1", "tcp://localhost:61616",
//    			new String[]{ "delete"} );
//    	consumer.run();
//    }
    
	public void run() {
    	try {
			for( Topic topic : topics ) {
//				Destination destination = getSession().createTopic(destId);
				MessageConsumer messageConsumer = 
					getSession().createConsumer( topic );
//				Listener l = new Listener();
//				l.consumerId = id;
				messageConsumer.setMessageListener(listener);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}	
	}
	
//	public abstract Destination getDestination( String destId ) throws JMSException;
	
	public Session getSession() {
		return session;
	}
}
