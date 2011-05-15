package org.jpatest.domain;

import java.lang.String;

privileged aspect Proposal_Roo_ToString {
    
    public String Proposal.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("Id: ").append(getId()).append(", ");        
        sb.append("Version: ").append(getVersion()).append(", ");        
        sb.append("Number: ").append(getNumber()).append(", ");        
        sb.append("Company: ").append(getCompany()).append(", ");        
        sb.append("Schedules: ").append(getSchedules() == null ? "null" : getSchedules().size());        
        return sb.toString();        
    }    
    
}
