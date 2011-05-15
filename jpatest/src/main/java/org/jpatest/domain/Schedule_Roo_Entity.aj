package org.jpatest.domain;

import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.jpatest.domain.Schedule;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Schedule_Roo_Entity {
    
    @PersistenceContext    
    transient EntityManager Schedule.entityManager;    
    
    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name = "id")    
    private Long Schedule.id;    
    
    @Version    
    @Column(name = "version")    
    private Integer Schedule.version;    
    
    public Long Schedule.getId() {    
        return this.id;        
    }    
    
    public void Schedule.setId(Long id) {    
        this.id = id;        
    }    
    
    public Integer Schedule.getVersion() {    
        return this.version;        
    }    
    
    public void Schedule.setVersion(Integer version) {    
        this.version = version;        
    }    
    
    @Transactional    
    public void Schedule.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @Transactional    
    public void Schedule.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            Schedule attached = this.entityManager.find(Schedule.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @Transactional    
    public void Schedule.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @Transactional    
    public void Schedule.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        Schedule merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static final EntityManager Schedule.entityManager() {    
        EntityManager em = new Schedule().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long Schedule.countSchedules() {    
        return (Long) entityManager().createQuery("select count(o) from Schedule o").getSingleResult();        
    }    
    
    public static List<Schedule> Schedule.findAllSchedules() {
    	String jpql = "SELECT s FROM Schedule s JOIN FETCH s.proposal JOIN FETCH s.proposal.company";
        return entityManager().createQuery(jpql).getResultList();        
    }    
    
    public static Schedule Schedule.findSchedule(Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of Schedule");        
        return entityManager().find(Schedule.class, id);        
    }    
    
    public static List<Schedule> Schedule.findScheduleEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from Schedule o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
