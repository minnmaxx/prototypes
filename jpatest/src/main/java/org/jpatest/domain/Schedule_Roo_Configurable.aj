package org.jpatest.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect Schedule_Roo_Configurable {
    
    declare @type: Schedule: @Configurable;    
    
}
