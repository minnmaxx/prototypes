package rpc;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;

import org.springframework.orm.hibernate3.HibernateTemplate;


public class RpcServer {

	@Resource(name="jmsConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	@Resource(name="service1")
	private IIdService service1;

	@Resource(name="service2")
	private INameService service2;	
	
	@PostConstruct
	private void init() {
		
		org.apache.activemq.broker.BrokerService b;
		org.apache.activemq.broker.TransportConnector t;
		HibernateTemplate t1;
		
	}

}
