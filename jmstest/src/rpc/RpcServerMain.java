package rpc;

import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

public class RpcServerMain {

	
    public static void main(String[] args) throws Exception {

    	ApplicationContext context = new FileSystemXmlApplicationContext( new String[] { 
			"src/resources/spring-vm.xml"
    	} );

    	Topic topic = new ActiveMQTopic( "random" );
    	
    	JmsTemplate template = (JmsTemplate) context.getBean( "jmsTemplate" );
    	template.convertAndSend( topic, "message" );
    	
    	SingleConnectionFactory factory = (SingleConnectionFactory) context.getBean( "jmsConnectionFactory" );
    	factory.resetConnection();
    	
//    	new FileSystemXmlApplicationContext( new String[] { 
//    			"src/resources/spring-rpc-server.xml",
//    			"src/resources/spring-rpc-share.xml"
//    	} );
//    	
//    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//
//    	String command = "";
//    	while( !"exit".equals( command ) ) {
//    		
//    		command = br.readLine();
//    	}
    }
}
