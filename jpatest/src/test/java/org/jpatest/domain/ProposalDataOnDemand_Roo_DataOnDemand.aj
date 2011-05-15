package org.jpatest.domain;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import org.jpatest.domain.Proposal;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ProposalDataOnDemand_Roo_DataOnDemand {
    
    declare @type: ProposalDataOnDemand: @Component;    
    
    private Random ProposalDataOnDemand.rnd = new SecureRandom();    
    
    private List<Proposal> ProposalDataOnDemand.data;    
    
    public Proposal ProposalDataOnDemand.getNewTransientProposal(int index) {    
        org.jpatest.domain.Proposal obj = new org.jpatest.domain.Proposal();        
        obj.setNumber("number_" + index);        
        return obj;        
    }    
    
    public Proposal ProposalDataOnDemand.getRandomProposal() {    
        init();        
        Proposal obj = data.get(rnd.nextInt(data.size()));        
        return Proposal.findProposal(obj.getId());        
    }    
    
    public boolean ProposalDataOnDemand.modifyProposal(Proposal obj) {    
        return false;        
    }    
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)    
    public void ProposalDataOnDemand.init() {    
        if (data != null) {        
            return;            
        }        
                
        data = org.jpatest.domain.Proposal.findProposalEntries(0, 10);        
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Proposal' illegally returned null");        
        if (data.size() > 0) {        
            return;            
        }        
                
        data = new java.util.ArrayList<org.jpatest.domain.Proposal>();        
        for (int i = 0; i < 10; i++) {        
            org.jpatest.domain.Proposal obj = getNewTransientProposal(i);            
            obj.persist();            
            data.add(obj);            
        }        
    }    
    
}
