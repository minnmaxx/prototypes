package test;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath:/resources/local-module.xml",
		"file:../testing-1/src/external-module.xml"
} )
public class MyTest {

	@Autowired(required=true)
	private HashMap map;
	
	@Test
	public void loadFile() {
		
		Assert.assertNotNull( map );
		
//		FileSystemResourceLoader loader = new FileSystemResourceLoader();
//		Resource resource = loader.getResource( "file:../testing-1/src/external-module.xml");
//		Assert.assertNotNull( resource );
//		
//		resource = loader.getResource( "file:../testing-1/src/non-existing.xml");
//		Assert.assertNull( resource );
	}
}
