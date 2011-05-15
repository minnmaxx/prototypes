package pubsub;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import common.Listener;

public class ConsumerAMQ {
	
    //private static String brokerURL = "tcp://localhost:61616";
    private static transient ConnectionFactory factory;
    private transient Connection connection;
    private transient Session session;
    private String[] destIds;
    private String id;
    
    public ConsumerAMQ(String id, String brokerUrl, String[] destIds) throws JMSException {
    	factory = new ActiveMQConnectionFactory(brokerUrl);
    	connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        this.id = id;
        this.destIds = destIds;
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
			for (String destId : destIds) {
				Destination destination = getSession().createTopic(destId);
				MessageConsumer messageConsumer = getSession().createConsumer(destination);
				Listener l = new Listener();
				l.consumerId = id;
				messageConsumer.setMessageListener(l);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}	
	}
	
	public Session getSession() {
		return session;
	}
}
