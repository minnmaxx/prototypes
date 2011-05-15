package org.jpatest.domain;

import java.lang.String;
import java.util.Set;
import org.jpatest.domain.Proposal;

privileged aspect Company_Roo_JavaBean {
    
    public Set<Proposal> Company.getProposals() {    
        return this.proposals;        
    }    
    
    public void Company.setProposals(Set<Proposal> proposals) {    
        this.proposals = proposals;        
    }    
    
    public String Company.getName() {    
        return this.name;        
    }    
    
    public void Company.setName(String name) {    
        this.name = name;        
    }    
    
}
