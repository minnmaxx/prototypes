package org.jpatest.domain;

import java.lang.String;

privileged aspect Schedule_Roo_ToString {
    
    public String Schedule.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("Id: ").append(getId()).append(", ");        
        sb.append("Version: ").append(getVersion()).append(", ");        
        sb.append("Proposal: ").append(getProposal()).append(", ");        
        sb.append("Name: ").append(getName());        
        return sb.toString();        
    }    
    
}
