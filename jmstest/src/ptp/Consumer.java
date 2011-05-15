package ptp;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import common.Listener;

public class Consumer implements Runnable {

    //private static String brokerURL = "tcp://localhost:61616";
    private static transient ConnectionFactory factory;
    private transient Connection connection;
    private transient Session session;
    
    private String jobs[] = new String[]{"suspend", "delete"};
    
    public Consumer( String brokerUrl) throws JMSException {
    	factory = new ActiveMQConnectionFactory(brokerUrl);
    	connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }
    
    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }    
    
    public static void main(String[] args) throws JMSException {
    	Consumer consumer = new Consumer( "tcp://localhost:61616" );
    	consumer.run();
    }
    
    
	
	public Session getSession() {
		return session;
	}

	public void run() {
    	try {
			for (String job : jobs) {
				Destination destination = getSession().createQueue("JOBS." + job);
				MessageConsumer messageConsumer = getSession().createConsumer(destination);
				//messageConsumer.setMessageListener(new Listener(job));
				messageConsumer.setMessageListener(new Listener());
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}	
	}
}

