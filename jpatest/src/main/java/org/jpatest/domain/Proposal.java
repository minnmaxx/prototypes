package org.jpatest.domain;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@javax.persistence.Entity
@org.springframework.roo.addon.javabean.RooJavaBean
@org.springframework.roo.addon.tostring.RooToString
@org.springframework.roo.addon.entity.RooEntity
public class Proposal {

    private String number;

    @javax.persistence.ManyToOne
    @javax.persistence.JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(cascade = CascadeType.ALL, fetch = javax.persistence.FetchType.LAZY, mappedBy = "proposal")
    //@Fetch(value=FetchMode.JOIN)
    private java.util.Set<org.jpatest.domain.Schedule> schedules = new java.util.HashSet<org.jpatest.domain.Schedule>();
}
