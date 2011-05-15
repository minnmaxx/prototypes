package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import model.MajorStageExecution;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class MajorStageExecutionService {

    @PersistenceContext(unitName="PersistenceUnit")
    private EntityManager entityManager;
    
    private Object lock = new Object();
    
	
	@Transactional
	public MajorStageExecution create(  ) {
		
		MajorStageExecution execution = new MajorStageExecution();
		entityManager.persist( execution );
		return execution;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<MajorStageExecution> getAll() {
		return entityManager.createQuery("SELECT o FROM MajorStageExecution o").getResultList();   
	}

	
	public MajorStageExecution getExisting(MajorStageExecution execution) {
		
		String jpql = "SELECT e FROM MajorStageExecution e WHERE e.id = ?1";
		
		Query query = entityManager.createQuery( jpql );
		query.setParameter(1, execution.getId());
		
		return (MajorStageExecution) query.getSingleResult(); 
	} 
	
	

	
	
	public long countAll() {
		return (Long) entityManager.createQuery("SELECT COUNT(o) FROM MajorStageExecution o").getSingleResult(); 
	}   
	
	
	@Transactional
	public void delete(MajorStageExecution query) {
    	MajorStageExecution attached = 
    		entityManager.find(MajorStageExecution.class, query.getId()); 
    	if( attached != null ) {
    		entityManager.remove(attached);
    		entityManager.flush();
    	}
	}

	
	
	@Transactional
	public MajorStageExecution updateEndTime(MajorStageExecution execution) {
		
		synchronized( lock ) {
	    	MajorStageExecution attached = 
	    		entityManager.find(MajorStageExecution.class, execution.getId()); 
			
			if( attached != null ) {
				
				attached.setEndTime( execution.getEndTime() );
				entityManager.flush();
				
			} else {
				throw new RuntimeException( "Updating an non existing MajorStageExecution is not allowed." );
			}
			
			return execution;
		}
	}

	
	@Transactional
	public MajorStageExecution updateStartTime(MajorStageExecution execution) {

		synchronized( lock ) {
			
			MajorStageExecution attached = 
	    		entityManager.find(MajorStageExecution.class, execution.getId()); 
			
			if( attached != null ) {
				
				attached.setStartTime( execution.getStartTime() );
				entityManager.flush();
				
			} else {
				throw new RuntimeException( "Updating an non existing MajorStageExecution is not allowed." );
			}
			
			return execution;
		}
	}


}
