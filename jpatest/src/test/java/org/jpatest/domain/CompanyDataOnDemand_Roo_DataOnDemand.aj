package org.jpatest.domain;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import org.jpatest.domain.Company;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

privileged aspect CompanyDataOnDemand_Roo_DataOnDemand {
    
    declare @type: CompanyDataOnDemand: @Component;    
    
    private Random CompanyDataOnDemand.rnd = new SecureRandom();    
    
    private List<Company> CompanyDataOnDemand.data;    
    
    public Company CompanyDataOnDemand.getNewTransientCompany(int index) {    
        org.jpatest.domain.Company obj = new org.jpatest.domain.Company();        
        obj.setName("name_" + index);        
        return obj;        
    }    
    
    public Company CompanyDataOnDemand.getRandomCompany() {    
        init();        
        Company obj = data.get(rnd.nextInt(data.size()));        
        return Company.findCompany(obj.getId());        
    }    
    
    public boolean CompanyDataOnDemand.modifyCompany(Company obj) {    
        return false;        
    }    
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)    
    public void CompanyDataOnDemand.init() {    
        if (data != null) {        
            return;            
        }        
                
        data = org.jpatest.domain.Company.findCompanyEntries(0, 10);        
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Company' illegally returned null");        
        if (data.size() > 0) {        
            return;            
        }        
                
        data = new java.util.ArrayList<org.jpatest.domain.Company>();        
        for (int i = 0; i < 10; i++) {        
            org.jpatest.domain.Company obj = getNewTransientCompany(i);            
            obj.persist();            
            data.add(obj);            
        }        
    }    
    
}
