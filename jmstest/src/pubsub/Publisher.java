package pubsub;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Publisher implements Runnable {

    protected int count = 1;
    protected int total;
    private int id = 1000000;
    
    //protected static String brokerURL = "tcp://localhost:61616";
    protected static transient ConnectionFactory factory;
    protected transient Connection connection;
    protected transient Session session;
    protected transient MessageProducer producer;
    private int numMessages;
    private String destId;
    
    public Publisher(String brokerUrl,String destId,int numMessages) throws JMSException {
    	factory = new ActiveMQConnectionFactory(brokerUrl);
    	connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(null);
        this.destId = destId;
        this.numMessages = numMessages;
    }
    
    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }
    
//    public static void main(String[] args) throws JMSException {
//    	Publisher publisher = new Publisher("tcp://localhost:61616","delete", 3);
//    	publisher.run();
//    }
    
	public void run() {
		
        try {
			
        	while (total < numMessages) {
        		for (int i = 0; i < count; i++) {
			    	sendMessage();
			    }
			    total += count;
			    
			    //System.out.println("Published: [id=" + count + "' of '" + total + "' job messages");
			    
			    Thread.sleep(1000);
        	}
			close();
			
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
	    }
	}

    protected void sendMessage() throws JMSException {
        
        Destination destination = session.createTopic( destId );
        Message message = session.createObjectMessage(id++);
        producer.send(destination, message);
        System.out.println("Sent: [dest=" + destination + ",id=" + ((ObjectMessage)message).getObject() + "]" );
    }
}
