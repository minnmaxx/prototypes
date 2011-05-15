package ptp;

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

    //private static String brokerURL = "tcp://localhost:61616";
    private static transient ConnectionFactory factory;
    private transient Connection connection;
    private transient Session session;
    private transient MessageProducer producer;
    private int numMessages;
    
    private static int count = 1;
    private static int total;
    private static int id = 1000000;
    
    private String jobs[] = new String[]{"suspend", "delete"};
    
    public Publisher( String brokerUrl, int numMessages ) throws JMSException {
    	factory = new ActiveMQConnectionFactory( brokerUrl );
    	connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(null);
        this.numMessages = numMessages;
    }    
    
    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }    
    
//	public static void main(String[] args) throws JMSException {
//    	Publisher publisher = new Publisher("tcp://localhost:61616",1000);
//    	publisher.run();
//	}
	
    public void sendMessage() throws JMSException {
        int idx = 0;
        while (true) {
            idx = (int)Math.round(jobs.length * Math.random());
            if (idx < jobs.length) {
                break;
            }
        }
        String job = jobs[idx];
        Destination destination = session.createQueue("JOBS." + job);
        Message message = session.createObjectMessage(id++);
        //System.out.println("Sending: id: " + ((ObjectMessage)message).getObject() + " on queue: " + destination);
        producer.send(destination, message);
        System.out.println("Sent: [dest=" + destination + ",id=" + ((ObjectMessage)message).getObject() + "]" );
    }

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

}

