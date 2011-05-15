package org.jpatest.domain;

import java.lang.String;
import java.util.Set;
import org.jpatest.domain.Company;
import org.jpatest.domain.Schedule;

privileged aspect Proposal_Roo_JavaBean {
    
    public String Proposal.getNumber() {    
        return this.number;        
    }    
    
    public void Proposal.setNumber(String number) {    
        this.number = number;        
    }    
    
    public Company Proposal.getCompany() {    
        return this.company;        
    }    
    
    public void Proposal.setCompany(Company company) {    
        this.company = company;        
    }    
    
    public Set<Schedule> Proposal.getSchedules() {    
        return this.schedules;        
    }    
    
    public void Proposal.setSchedules(Set<Schedule> schedules) {    
        this.schedules = schedules;        
    }    
    
}
