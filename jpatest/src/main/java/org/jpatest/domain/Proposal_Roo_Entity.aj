package org.jpatest.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Version;

import org.springframework.transaction.annotation.Transactional;

privileged aspect Proposal_Roo_Entity {
    
    @PersistenceContext    
    transient EntityManager Proposal.entityManager;    
    
    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name = "id")    
    private Long Proposal.id;    
    
    @Version    
    @Column(name = "version")    
    private Integer Proposal.version;    
    
    public Long Proposal.getId() {    
        return this.id;        
    }    
    
    public void Proposal.setId(Long id) {    
        this.id = id;        
    }    
    
    public Integer Proposal.getVersion() {    
        return this.version;        
    }    
    
    public void Proposal.setVersion(Integer version) {    
        this.version = version;        
    }    
    
    @Transactional    
    public void Proposal.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @Transactional    
    public void Proposal.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            Proposal attached = this.entityManager.find(Proposal.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @Transactional    
    public void Proposal.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @Transactional    
    public void Proposal.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        Proposal merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static final EntityManager Proposal.entityManager() {    
        EntityManager em = new Proposal().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long Proposal.countProposals() {    
        return (Long) entityManager().createQuery("select count(o) from Proposal o").getSingleResult();        
    }    
    
    public static List<Proposal> Proposal.findAllProposals() {
    	
    	Boolean useJPQL = true;
    	
    	if ( !useJPQL )
    	{
//	    	CriteriaBuilder cb = entityManager().getCriteriaBuilder();
//	    	CriteriaQuery<Proposal> cq = cb.createQuery( Proposal.class );
//	    	Root<Proposal> p = cq.from(Proposal.class);
//	    	cq.select(p);
//	    	
//	    	TypedQuery<Proposal> q = entityManager().createQuery(cq);
//	    	
//	    	// this would fail
//	    	return q.getResultList();
    		return new ArrayList<Proposal>();
    	}
    	else
    	{
    		//Query query = entityManager().createQuery("select p from Proposal p");
    		Query query = entityManager().createQuery("SELECT p FROM Proposal p JOIN FETCH p.company");
    		return query.getResultList();
    	}
    	
    	
    	//Query query = entityManager().createQuery("select o from Proposal o");
    	
    	
    	
        //return query.getResultList();
    }    
    
    public static Proposal Proposal.findProposal(Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of Proposal");        
        return entityManager().find(Proposal.class, id);        
    }    
    
    public static List<Proposal> Proposal.findProposalEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from Proposal o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
