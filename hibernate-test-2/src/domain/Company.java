package domain;

import java.util.HashSet;
import java.util.Set;


public class Company {

    private Set<Proposal> proposals = new HashSet<Proposal>();

    private String name;
    
    private Long id;    
    
    private Integer version;    
    
    public Long getId() {    
        return this.id;        
    }    
    
    public void setId(Long id) {    
        this.id = id;        
    }    
    
    public Integer getVersion() {    
        return this.version;        
    }    
    
    public void setVersion(Integer version) {    
        this.version = version;        
    }   
    
    public Set<Proposal> getProposals() {    
        return this.proposals;        
    }    
    
    public void setProposals(Set<Proposal> proposals) {    
        this.proposals = proposals;        
    }    
    
    public String getName() {    
        return this.name;        
    }    
    
    public void setName(String name) {    
        this.name = name;        
    } 
    
    public String toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("Id: ").append(getId()).append(", ");        
        sb.append("Version: ").append(getVersion()).append(", ");        
        //sb.append("Proposals: ").append(getProposals() == null ? "null" : getProposals().size()).append(", ");        
        sb.append("Name: ").append(getName());        
        return sb.toString();        
    }    
    
}
