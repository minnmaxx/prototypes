package org.jpatest.domain;

import java.lang.String;

privileged aspect Company_Roo_ToString {
    
    public String Company.toString() {    
        StringBuilder sb = new StringBuilder();        
        sb.append("Id: ").append(getId()).append(", ");        
        sb.append("Version: ").append(getVersion()).append(", ");        
        sb.append("Proposals: ").append(getProposals() == null ? "null" : getProposals().size()).append(", ");        
        sb.append("Name: ").append(getName());        
        return sb.toString();        
    }    
    
}
