package test;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import service.DataService;
import domain.Company;
import domain.Proposal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath:applicationContext.xml" })
public class DataServiceTests {
	
	@Autowired(required=true)
	private DataService service;
	
	@Test
	public void wired() {
		Assert.assertNotNull( service );
	}
	
	@Test
	public void getProposals() {
		List<Proposal> proposals = service.getProposals();
		
		Assert.assertEquals(4, proposals.size());
		
		for( Proposal p : proposals ) {
			System.out.print( p.getNumber() );
			System.out.print( " " );
			System.out.println( p.getCompany().getName() );
		}
	}
	
	@Test
	public void getSmallNumberOfProposals() {
		Company company = new Company();
		company.setId(1L);
		
		List<Proposal> proposals = service.getProposals(company);
		
		Assert.assertEquals(2, proposals.size());
		
		for( Proposal p : proposals ) {
			System.out.print( p.getNumber() );
			System.out.print( " " );
			System.out.println( p.getCompany().getName() );
		}
	}
}
