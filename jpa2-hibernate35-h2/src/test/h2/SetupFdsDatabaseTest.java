package h2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@ContextConfiguration(locations = { 
		"classpath:/META-INF/spring/database-context.xml"
} )
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
public class SetupFdsDatabaseTest extends AbstractTestNGSpringContextTests {

	@Autowired(required=true)
	private DataSource dataSource;
	
	private Connection connection;
	
	@BeforeClass
	public void setUp() throws SQLException {
		Assert.assertNotNull(dataSource);
		connection = DataSourceUtils.getConnection(dataSource);
		Assert.assertNotNull(connection);
	}
	
	@Test
	public void testRetrieve() throws SQLException {
		
		Assert.assertNotNull(connection);
		
		Statement statement = connection.createStatement();
		
		ResultSet rs;
		
		rs = statement.executeQuery( "SELECT COUNT(1) AS c FROM Company WHERE name='Fractech';" );
		rs.next();
		Assert.assertEquals( 1, rs.getInt("c") );
		rs.close();		

		rs = statement.executeQuery( "SELECT COUNT(1) AS c FROM Well WHERE name='Simulation Well';" );
		rs.next();
		Assert.assertEquals( 1, rs.getInt("c") );
		rs.close();	
	}
	
	@AfterClass
	public void tearDown() throws SQLException {
		connection.close();
	}
}
