package service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

import domain.Company;
import domain.Proposal;

public class DataService {

	@Autowired(required=true)
	private HibernateTemplate hibernate;
	
	public List<Proposal> getProposals() {
		
		return hibernate.loadAll(Proposal.class);
	}
	
	// get proposal by company
	public List<Proposal> getProposals(Company company)
	{
		DetachedCriteria query = DetachedCriteria.forClass(Proposal.class);
		DetachedCriteria companyQuery = query.createCriteria("company");
		companyQuery.add( Restrictions.eq("id", company.getId()) );
		return (List<Proposal>) hibernate.findByCriteria(query);
	}
}
