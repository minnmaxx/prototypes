package org.jpatest.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@javax.persistence.Entity
@org.springframework.roo.addon.javabean.RooJavaBean
@org.springframework.roo.addon.tostring.RooToString
@org.springframework.roo.addon.entity.RooEntity
public class Schedule {

    @javax.persistence.ManyToOne(targetEntity = org.jpatest.domain.Proposal.class)
    @javax.persistence.JoinColumn(name = "proposal_id")
    @Fetch(FetchMode.JOIN)
    private org.jpatest.domain.Proposal proposal;

    private String name;
}
