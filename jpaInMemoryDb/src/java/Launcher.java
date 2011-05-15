
import java.sql.Connection;
import java.sql.Statement;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import model.Item;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DataSourceUtils;

import dao.IItemDao;


public class Launcher {
	
	private static abstract class UpdateTask implements Runnable {
		
		IItemDao dao;
		CountDownLatch latch;
		long id;
		Item result;
		UpdateTask( IItemDao dao, long id, CountDownLatch latch ) {
			this.dao = dao;
			this.id = id;
			this.latch = latch;
		}
		
		@Override
		public void run() {

			Item item = dao.find( id );
			System.out.println( item.hashCode() );
			latch.countDown();
			try {
				latch.await(10,TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println( "trying to update at " + (new Date()).getTime() );
			result = editItem( item );
		}
		
		abstract Item editItem( Item item );
	}

	// jdbc:hsqldb:file:testdb;shutdown=true;ifexists=true
	
	public static void main(String[] args) throws Exception {

    	ApplicationContext context = new ClassPathXmlApplicationContext( new String[] {
    			"classpath:/META-INF/spring/module-context.xml"
        } );
    	
    	DataSource ds = (DataSource) context.getBean( "dataSource" );
    	
    	createTable( ds );

    	IItemDao dao = (IItemDao) context.getBean( "itemDao" );
    	
    	for( long i = 1; i <=4 ; i++ ) {
    		Item item = new Item();
    		item.setId( i );
    		item.setProperty1( i+"-p1");
    		item.setProperty2( i+"-p2");
    		dao.add( item );
    	}
    	
    	runThreads( dao );
	}
	
	public static void runThreads(IItemDao dao) throws Exception {
    	int numThreads = 2;
    	CountDownLatch latch = new CountDownLatch(numThreads);
    	
    	UpdateTask task1 = new UpdateTask(dao,1L,latch) {
			@Override
			Item editItem(Item item) {
				item.setProperty2( "p2=task1");
				return dao.setProperty2( item );
			}
		};
    	UpdateTask task2 = new UpdateTask(dao,1L,latch) {
			@Override
			Item editItem(Item item) {
				item.setProperty1( "p1=task2");
				return dao.setProperty1( item );
			}
		};	
    	
    	Thread t1 = new Thread( task1 );
    	Thread t2 = new Thread( task2 );
    	
    	t1.start();
    	t2.start();
    	
    	t1.join();
    	t2.join();
    	
    	System.out.println( task1.result );
    	System.out.println( task2.result );
	}
	
	public static void createTable( DataSource ds ) throws Exception {
		String sqlCreateTable = 
			"CREATE TABLE Item ( " +
			"  id BIGINT, " +
			"  property1 VARCHAR(255), " +
			"  property2 VARCHAR(255)  " +
			"); "; 
		Connection c = DataSourceUtils.getConnection(ds);
		
        Statement statement = c.createStatement();
        statement.addBatch( sqlCreateTable );
        statement.executeBatch();
		
        if( statement != null ) {
        	statement.close();
        }
	}
}
