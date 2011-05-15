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
import org.jpatest.domain.Company;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Company_Roo_Entity {
    
    @PersistenceContext    
    transient EntityManager Company.entityManager;    
    
    @Id    
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name = "id")    
    private Long Company.id;    
    
    @Version    
    @Column(name = "version")    
    private Integer Company.version;    
    
    public Long Company.getId() {    
        return this.id;        
    }    
    
    public void Company.setId(Long id) {    
        this.id = id;        
    }    
    
    public Integer Company.getVersion() {    
        return this.version;        
    }    
    
    public void Company.setVersion(Integer version) {    
        this.version = version;        
    }    
    
    @Transactional    
    public void Company.persist() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.persist(this);        
    }    
    
    @Transactional    
    public void Company.remove() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        if (this.entityManager.contains(this)) {        
            this.entityManager.remove(this);            
        } else {        
            Company attached = this.entityManager.find(Company.class, this.id);            
            this.entityManager.remove(attached);            
        }        
    }    
    
    @Transactional    
    public void Company.flush() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        this.entityManager.flush();        
    }    
    
    @Transactional    
    public void Company.merge() {    
        if (this.entityManager == null) this.entityManager = entityManager();        
        Company merged = this.entityManager.merge(this);        
        this.entityManager.flush();        
        this.id = merged.getId();        
    }    
    
    public static final EntityManager Company.entityManager() {    
        EntityManager em = new Company().entityManager;        
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");        
        return em;        
    }    
    
    public static long Company.countCompanys() {    
        return (Long) entityManager().createQuery("select count(o) from Company o").getSingleResult();        
    }    
    
    public static List<Company> Company.findAllCompanys() {
        return entityManager().createQuery("select o from Company o").getResultList();        
    }
    
    public static List<Company> Company.findAllWithProposals() {
    	String jpql = "SELECT DISTINCT c FROM Company c JOIN FETCH c.proposals";
    	//Use left join when all companies should be loaded regardless their proposal status 
    	//String jpql = "SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.proposals";
    	return entityManager().createQuery(jpql).getResultList();
    }
    
    public static Company Company.findCompany(Long id) {    
        if (id == null) throw new IllegalArgumentException("An identifier is required to retrieve an instance of Company");        
        return entityManager().find(Company.class, id);        
    }    
    
    public static List<Company> Company.findCompanyEntries(int firstResult, int maxResults) {    
        return entityManager().createQuery("select o from Company o").setFirstResult(firstResult).setMaxResults(maxResults).getResultList();        
    }    
    
}
