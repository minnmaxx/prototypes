package org.jpatest.domain;

import java.lang.String;
import org.jpatest.domain.Proposal;

privileged aspect Schedule_Roo_JavaBean {
    
    public Proposal Schedule.getProposal() {    
        return this.proposal;        
    }    
    
    public void Schedule.setProposal(Proposal proposal) {    
        this.proposal = proposal;        
    }    
    
    public String Schedule.getName() {    
        return this.name;        
    }    
    
    public void Schedule.setName(String name) {    
        this.name = name;        
    }    
    
}
