package org.jpatest.domain;

import org.springframework.beans.factory.annotation.Configurable;

privileged aspect Proposal_Roo_Configurable {
    
    declare @type: Proposal: @Configurable;    
    
}
