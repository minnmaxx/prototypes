package org.jpatest.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect CompanyDataOnDemand_Roo_Configurable {
    
    declare @type: CompanyDataOnDemand: @Configurable;    
    
}
