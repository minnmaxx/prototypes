package org.jpatest.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;


@javax.persistence.Entity
@org.springframework.roo.addon.javabean.RooJavaBean
@org.springframework.roo.addon.tostring.RooToString
@org.springframework.roo.addon.entity.RooEntity
public class Company {

    @OneToMany(cascade=CascadeType.ALL,mappedBy="company",fetch=FetchType.LAZY)
    //@Fetch(value=FetchMode.JOIN)
    private Set<Proposal> proposals = new HashSet<Proposal>();

    private String name;
}
