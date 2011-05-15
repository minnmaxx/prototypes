package broker;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.util.Assert;

import ptp.Consumer;
import ptp.Publisher;

public class PtpMain {
	
	String brokerName = "myBroker";
	String brokerUrl = "vm://" + brokerName;

	public BrokerService createBroker() throws Exception {
		
		BrokerService broker = new BrokerService();
		
		broker.setUseJmx( false );
		broker.setBrokerName( brokerName );
		broker.setPersistent( false );
		broker.setEnableStatistics( false );
		broker.setUseShutdownHook( true );
		
		broker.addConnector( brokerUrl );
		
		return broker;
	}
	
	public BrokerService createBrokerXml() throws Exception {
		
		BrokerService broker = BrokerFactory.createBroker( new URI(
				"xbean:resources/broker.xml" ));
		
		return broker;
	}
	
	public void createBrokerSpring() throws Exception {
		
		String config = "src/resources/spring.xml";
		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(config);
		
		
	}
	
//	public static void main(String[] args) throws Exception {
//		
//		createBrokerSpring();
//	}
	
	public void run() throws Exception {
    	
    	ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
    	
//    	BrokerService broker = createBroker();
//    	broker.start();
    	
//    	createBrokerSpring();
    	
    	int seconds = 2;
    	
    	// start publisher
    	Publisher publisher = new Publisher( brokerUrl, seconds-1 );
    	Future<?> publisherFuture = executor.submit( publisher );
    	
    	// start consumer
    	Consumer consumer = new Consumer(brokerUrl);
    	Future<?> consumerFuture = executor.submit( consumer );
    	
    	Thread.currentThread().sleep( seconds * 1000 );
    	
    	Assert.isNull( publisherFuture.get() );
    	Assert.isNull( consumerFuture.get() );
    	
    	// start publisher again
    	
    	
    	
//    	Thread.currentThread().sleep( 30 * 1000 );
//    	System.out.println( broker.isStarted() );
    	
    	executor.shutdownNow();
    	
    	//broker.stop();
    	
    	System.out.println( "all done" );		
	}
	
//	@Autowired(required=true)
//	private BrokerService broker;
	
    public static void main(String[] args) throws Exception {
    	
//    	ClassPathResource resource = new ClassPathResource( "/resources/spring.xml" );
//    	XmlBeanFactory factory = new XmlBeanFactory( resource );
//    	
//    	String[] names = factory.getBeanNamesForType( BrokerService.class );
//    	System.out.println( Arrays.toString( names ) );
//    	BrokerService broker = (BrokerService) factory.getBean( names[0] );
    	
    	FileSystemXmlApplicationContext context = 
    		new FileSystemXmlApplicationContext( "src/resources/spring.xml" );
    	
//    	Map brokers = context.getBeansOfType( BrokerService.class );
//    	for( Object name : brokers.keySet() ) {
//    		System.out.println( name );
//    	}
    	
    	(new PtpMain()).run();
    }
}
