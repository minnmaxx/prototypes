package org.jpatest.domain;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { 
		"classpath:/META-INF/spring/applicationContext.xml" })
public class DBTest {

//	@BeforeClass
//	public static void setup() {
//		try {
//			HibernateProfiler.initialize();
//		} catch( Exception e ) {
//			e.printStackTrace();
//		}
//		
//	}
	
	@Ignore @Test
	public void connect() {
		try {
			Class.forName("org.postgresql.Driver");
			Connection conn = 
				DriverManager.getConnection("jdbc:postgresql://localhost:5432/fdsdb", "localuser", "");
			
			Assert.assertNotNull( conn );
			
			Statement st = conn.createStatement();
			// Turn use of the cursor on.
			st.setFetchSize(50);
			ResultSet rs = st.executeQuery("SELECT * FROM company");
			while (rs.next()) {
			   System.out.println(rs.getString("name"));
			}
			rs.close();
			// Close the statement.
			st.close();
			
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    @Test    
    @Transactional
    public void testFindAllProposals() {
    	
        //long count = Proposal.countProposals();        

        //Assert.assertEquals(4,count);
        
        List<Proposal> result = Proposal.findAllProposals();        
        
        Assert.assertNotNull(result);        
        Assert.assertTrue(result.size() > 0);      
        
        for(Proposal p : result ) {
        	Assert.assertTrue( p.getId() > 0L );
        	Assert.assertNotNull( p.getNumber() );
        	Assert.assertNotNull( p.getCompany() );
        	Assert.assertTrue( p.getCompany().getId() > 0 );      
        }
    }
    
    
    @Test
    public void getCompaniesAndTheirProposals()
    {
    	List<Company> companies = Company.findAllWithProposals();
    	int size = companies.size();
    	Assert.assertTrue(companies.size() > 1);
    	Assert.assertTrue(companies.size() <= 3);
    	
    	for ( Company c : companies )
    	{
    		System.out.println ( c.getName() );
    		for ( Proposal p : c.getProposals() )
    		{
    			System.out.println( " - " + p.getNumber() );
    		}
    	}
    }
    
    @Test   
    public void testGetSchedules() {

    	List<Schedule> result = Schedule.findAllSchedules(); 
    	
    	//Proposal proposal = Proposal.findProposal(1L);
    	Assert.assertEquals( 2, result.size() );
    	
    	// throws
    	//s.getProposal().getSchedules().size();
    	
    	for( Schedule s : result )
    	{
	    	System.out.println( s.getName() );
	    	System.out.println( s.getProposal().getNumber() );
	    	System.out.println( s.getProposal().getCompany().getName() );
    	}
    	
    	//Assert.assertNotNull( proposal.getCompany() );
    	//Assert.assertEquals( 0, proposal.getCompany().getProposals().size() );
    	
    	//System.out.println( "company.proposals.size()=" + proposal.getCompany().getProposals().size() );
    }
    
    @Test   
    @Transactional
    public void testUpdateProposal() {

    	Proposal proposal = Proposal.findProposal(1L);
    	Assert.assertNotNull( proposal );
    	
    	String newNumber = "new proposal number";
    	proposal.setNumber( newNumber );
    	
    	Proposal proposalAfterUpdate = Proposal.findProposal(1L);
    	Assert.assertEquals(newNumber, proposalAfterUpdate.getNumber());
    }      
    
    @Test   
    @Transactional
    public void testAddProposal() {

    	Company company = Company.findCompany(1L);
    	
    	Proposal proposal = new Proposal();
    	proposal.setNumber( "Proposal 5");
    	proposal.setVersion(1);
    	proposal.setCompany(company);
    	proposal.persist();
    	
    	System.out.println( proposal );
    	
    	Proposal proposalAfterPersist = Proposal.findProposal(proposal.getId());
    	
    	Assert.assertNotNull( proposalAfterPersist );
    	
    	Assert.assertEquals( company.getId(), proposal.getCompany().getId() );
    }

}
