package pubsub;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Topic;

import common.AbstractConsumer;
import common.Listener;

public class Consumer extends AbstractConsumer {

	public Consumer(String id, ConnectionFactory factory, 
			Listener listener, Topic... destIds ) throws JMSException {
		super(id, factory, destIds, listener);

	}

}
