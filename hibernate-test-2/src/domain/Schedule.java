package domain;


public class Schedule {

    private domain.Proposal proposal;

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
    public Proposal getProposal() {    
        return this.proposal;        
    }    
    
    public void setProposal(Proposal proposal) {    
        this.proposal = proposal;        
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
        sb.append("Proposal: ").append(getProposal()).append(", ");        
        sb.append("Name: ").append(getName());        
        return sb.toString();        
    } 
}
