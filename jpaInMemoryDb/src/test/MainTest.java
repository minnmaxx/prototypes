
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CountDownLatch;

import javax.sql.DataSource;

import model.Item;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import dao.ItemDao;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath:/META-INF/spring/module-context.xml"
} )
@TestExecutionListeners(value = { 
		DependencyInjectionTestExecutionListener.class 
})
public class MainTest {

	@Autowired(required=true)
	private DataSource dataSource;
	
	@Autowired(required=true)
	private ItemDao dao;
	
	@Test
	public void testCreateTable() throws SQLException {
		String sqlCreateTable = 
			"CREATE TABLE Item ( " +
			"  id BIGINT, " +
			"  property1 VARCHAR(255), " +
			"  property2 VARCHAR(255)  " +
			"); "; 
		Connection c = DataSourceUtils.getConnection(dataSource);
		
        Statement statement = c.createStatement();
        statement.addBatch( sqlCreateTable );
        statement.executeBatch();
		
        if( statement != null ) {
        	statement.close();
        }
	}
	
	@Test
	public void testAddData() {
    	for( long i = 1; i <=4 ; i++ ) {
    		Item item = new Item();
    		item.setId( i );
    		item.setProperty1( i+"-p1");
    		item.setProperty2( i+"-p2");
    		item = dao.add( item );
    		Assert.assertNotNull( item );
    		Assert.assertEquals( i, dao.getAll().size() );
    	}
    	
    	Item item1 = dao.find( 1L );
    	Assert.assertNotNull( item1 );
    	Assert.assertEquals( 1L, item1.getId() );
	}
	

	@Test
	public void testRunThreads() throws InterruptedException {
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
}
