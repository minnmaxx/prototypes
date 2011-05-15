package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.ChannelDefinition;
import model.MajorStageExecution;
import model.Schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ChannelDefinitionService {

    @PersistenceContext(unitName="PersistenceUnit")
    private EntityManager entityManager;
    
    @Autowired(required=true)
    private MajorStageExecutionService executionService;

	@Transactional
    public ChannelDefinition add( ChannelDefinition channel ) {
    	
    	// reattach to execution
//    	MajorStageExecution attachedExecution = 
//    		executionService.getExisting( channel.getMajorStageExecution() );
//    	    	    	
//    	channel.setMajorStageExecution( attachedExecution );
    	
    	entityManager.persist(channel);
    	return channel;
    }
	
	@Transactional
	public Schema add( Schema schema ) {
		
		// attach execution
    	MajorStageExecution attachedExecution = 
    		executionService.getExisting( schema.getStageExecution() );
    	schema.setStageExecution( attachedExecution );
		
		// attach all channels
    	// implicitly updating the internal data structure
		List<ChannelDefinition> channels = schema.getChannelDefinitions();
		for( int i = 0; i < channels.size(); i++ ) {
			channels.set( i, add( channels.get( i ) ) );
		}
		
		entityManager.persist(schema);
		
		return schema;
	}
	
	
	public ChannelDefinition getExisting( ChannelDefinition channel ) {
		
		String strQuery = 
//			"SELECT a FROM ChannelDefinition a JOIN FETCH a.majorStageExecution e " +
//			"WHERE a.compositeId = ?1";
			"SELECT a FROM ChannelDefinition a " +
			"WHERE a.compositeId = ?1";
		
		Query query = entityManager.createQuery( strQuery );
		query.setParameter(1, channel.getCompositeId());
		
		channel = (ChannelDefinition) query.getSingleResult(); 
		
		return channel;
	}
}
