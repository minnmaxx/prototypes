package org.jpatest.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect Company_Roo_Configurable {
    
    declare @type: Company: @Configurable;    
    
}
