package common;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

public abstract class AbstractPublisher {

    private Connection connection;
    private Session session;
    
    protected String id;
    protected MessageProducer producer;
    
    protected int msgTotal;
    protected int msgId = 1000000;
    
    protected String destIds[] = new String[]{"suspend", "delete"};
    
    public AbstractPublisher( String id, ConnectionFactory factory, int msgTotal, String[] destIds ) throws JMSException {
    	connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(null);
        this.msgTotal = msgTotal;
        this.destIds = destIds;
        this.id = id;
    }    
    
    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }    

    abstract public void sendMessage() throws JMSException; 

	public void run() {
		
        try {
			
    		for (int i = 0; i < msgTotal; i++) {
		    	sendMessage();
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
