package h2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

public class DatabaseLoader {
	
	private static Logger logger = LoggerFactory.getLogger(DatabaseLoader.class);

	@Autowired(required=true)
    private DataSource dataSource;
    
	private final Resource[] scripts;

    public DatabaseLoader(Resource... scripts) {
        this.scripts = scripts;
    }

    public void populate() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            for( int i = 0; i < scripts.length; i++ ) {
            	executeScript(scripts[i],connection);
            }
        } catch (IOException e) {
            throw new RuntimeException("I/O exception occurred accessing the test data file", e);
        } catch (SQLException e) {
            throw new RuntimeException("SQL exception occurred loading test data", e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    private void executeScript(Resource resource, Connection connection) throws IOException, SQLException {
        InputStream is = null;
        String sql = null;
        try {
            is = resource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            StringWriter sw = new StringWriter();
            BufferedWriter writer = new BufferedWriter(sw);

            String line;
            while( (line = reader.readLine()) != null ) {
            	
            	line = line.trim();
            	
            	// skip comments --
            	if( line.isEmpty() || line.startsWith("--") ) {
            		continue;
            	}
            	
            	writer.write( line );
            	writer.write( ' ' );
            	if( line.endsWith( ";" ) ) {
            		writer.flush();
            		
            		sql = sw.toString();
            		executeSql( sql, connection );
            		
                    sw = new StringWriter();
                    writer = new BufferedWriter(sw);
            	}
            }

        } catch( SQLException e ) {
        	throw new RuntimeException("SQL exception occurred executing: [" + sql + "]" , e);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private void executeSql(String sql, Connection connection) throws SQLException {
    	
    	logger.debug( "[SQL] {}", sql );
    	
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }		
}
