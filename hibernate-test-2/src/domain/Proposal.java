package domain;

import java.util.HashSet;
import java.util.Set;

public class Proposal {

    private String number;

    private domain.Company company;

    private Set<domain.Schedule> schedules = new HashSet<domain.Schedule>();
    
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
    
    public String getNumber() {    
        return this.number;        
    }    
    
    public void setNumber(String number) {    
        this.number = number;        
    }    
    
    public Company getCompany() {    
        return this.company;        
    }    
    
    public void setCompany(Company company) {    
        this.company = company;        
    }    
    
    public Set<Schedule> getSchedules() {    
        return this.schedules;        
    }    
    
    public void setSchedules(Set<Schedule> schedules) {    
        this.schedules = schedules;        
    } 
    
    public String toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("Id: ").append(getId()).append(", ");        
        sb.append("Version: ").append(getVersion()).append(", ");        
        sb.append("Number: ").append(getNumber()).append(", ");        
        sb.append("Company: ").append(getCompany()).append(", ");        
        //sb.append("Schedules: ").append(getSchedules() == null ? "null" : getSchedules().size());        
        return sb.toString();        
    }    
}
