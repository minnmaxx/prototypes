package dao;

import java.util.ArrayList;
import java.util.List;

import model.ChannelDefinition;
import model.MajorStageExecution;
import model.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = { 
		"classpath:/META-INF/spring/persistence-context.xml",
		"classpath:/META-INF/spring/major-stage-execution-service.xml",
		"classpath:/META-INF/spring/channel-definition-service.xml",
		"classpath:/META-INF/spring/database-context.xml"
} )
public class ChannelDefinitionTest extends AbstractTestNGSpringContextTests {

	@Autowired(required=true)
	private MajorStageExecutionService executionService;
	
	@Autowired(required=true)
	private ChannelDefinitionService channelService;
	
	private MajorStageExecution execution;
	private ChannelDefinition channel1;
	
	@Test
	public void addMajorStageExecution() {
		
		execution = executionService.create( );
		
		MajorStageExecution query = new MajorStageExecution( execution.getId() );
		
		executionService.getExisting( query );
	}
	
	@Test(dependsOnMethods={"addMajorStageExecution"})
	public void addSchema() {
		
		channel1 = new ChannelDefinition.Builder()
			.id( 1 ).majorStageExecution( execution )
			.unit( 0 )
			.color( 0 )
			.description("channel1")
			.build();
		
		List<ChannelDefinition> channels = new ArrayList<ChannelDefinition>();
		
		Schema schema = new Schema.Builder()
			.sequence( 1 )
			.stageExecution( execution )
			.channelDefinitions( channels )
			.build();
		
		channelService.add( schema );
	}
	
//	@Test
//	@TestOrder(2)
//	public void addChannel() {
//		
//		channel1 = new ChannelDefinition.Builder()
//			.id( 1 ).majorStageExecution( execution )
//			.unit( ChannelUnit.AMOUNT_NONE )
//			.color( 0 )
//			.description("channel1")
//			.build();
//		
//		channel1 = channelService.add(channel1);
//		
//		ChannelDefinition channel2 = new ChannelDefinition.Builder()
//			.id( 2 ).majorStageExecution( execution )
//			.unit( ChannelUnit.AMOUNT_NONE )
//			.color( 0 )
//			.description("channel2")
//			.build();
//	
//		channel2 = channelService.add(channel2);		
//	}
//	
//	@Test
//	@TestOrder(3)
//	public void getChannel() {
//		
//		MajorStageExecution queryExecution = new MajorStageExecution( -1 );
//		
//		ChannelDefinition query = new ChannelDefinition.Builder()
//			.majorStageExecution(queryExecution)
//			.id( channel1.getId() )
//			.build();
//		
//		query.setMajorStageExecution( execution );
//		
//		ChannelDefinition result = channelService.getExisting( query );
//		
//		Assert.assertEquals( result.getDescription(), channel1.getDescription() );
//	}
	
//	@Test(expectedExceptions=RuntimeException.class)
//	@TestOrder(4)
//	public void addDuplicateChannel() {
//	
//		ChannelDefinition duplicate1 = new ChannelDefinition.Builder()
//			.id( 1 ).majorStageExecution( execution )
//			.unit( ChannelUnit.AMOUNT_NONE )
//			.color( 0 )
//			.description("channel2")
//			.build();
//
//		duplicate1 = channelService.add(duplicate1);		
//	}
}
