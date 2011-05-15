package org.jpatest.domain;

import org.jpatest.domain.ProposalDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ProposalIntegrationTest_Roo_IntegrationTest {
    
    declare @type: ProposalIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);    
    
    declare @type: ProposalIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");    
    
    @Autowired    
    private ProposalDataOnDemand ProposalIntegrationTest.dod;    
    
    @Test    
    public void ProposalIntegrationTest.testCountProposals() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Proposal' failed to initialize correctly", dod.getRandomProposal());        
        long count = org.jpatest.domain.Proposal.countProposals();        
        org.junit.Assert.assertTrue("Counter for 'Proposal' incorrectly reported there were no entries", count > 0);        
    }    
    
    @Test    
    public void ProposalIntegrationTest.testFindProposal() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Proposal' failed to initialize correctly", dod.getRandomProposal());        
        java.lang.Long id = dod.getRandomProposal().getId();        
        org.junit.Assert.assertNotNull("Data on demand for 'Proposal' failed to provide an identifier", id);        
        org.jpatest.domain.Proposal obj = org.jpatest.domain.Proposal.findProposal(id);        
        org.junit.Assert.assertNotNull("Find method for 'Proposal' illegally returned null for id '" + id + "'", obj);        
        org.junit.Assert.assertEquals("Find method for 'Proposal' returned the incorrect identifier", id, obj.getId());        
    }    
    
    @Test    
    public void ProposalIntegrationTest.testFindAllProposals() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Proposal' failed to initialize correctly", dod.getRandomProposal());        
        long count = org.jpatest.domain.Proposal.countProposals();        
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Proposal', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);        
        java.util.List<org.jpatest.domain.Proposal> result = org.jpatest.domain.Proposal.findAllProposals();        
        org.junit.Assert.assertNotNull("Find all method for 'Proposal' illegally returned null", result);        
        org.junit.Assert.assertTrue("Find all method for 'Proposal' failed to return any data", result.size() > 0);        
    }    
    
    @Test    
    public void ProposalIntegrationTest.testFindProposalEntries() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Proposal' failed to initialize correctly", dod.getRandomProposal());        
        long count = org.jpatest.domain.Proposal.countProposals();        
        if (count > 20) count = 20;        
        java.util.List<org.jpatest.domain.Proposal> result = org.jpatest.domain.Proposal.findProposalEntries(0, (int)count);        
        org.junit.Assert.assertNotNull("Find entries method for 'Proposal' illegally returned null", result);        
        org.junit.Assert.assertEquals("Find entries method for 'Proposal' returned an incorrect number of entries", count, result.size());        
    }    
    
    @Test    
    @Transactional    
    public void ProposalIntegrationTest.testFlush() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Proposal' failed to initialize correctly", dod.getRandomProposal());        
        java.lang.Long id = dod.getRandomProposal().getId();        
        org.junit.Assert.assertNotNull("Data on demand for 'Proposal' failed to provide an identifier", id);        
        org.jpatest.domain.Proposal obj = org.jpatest.domain.Proposal.findProposal(id);        
        org.junit.Assert.assertNotNull("Find method for 'Proposal' illegally returned null for id '" + id + "'", obj);        
        boolean modified =  dod.modifyProposal(obj);        
        java.lang.Integer currentVersion = obj.getVersion();        
        obj.flush();        
        org.junit.Assert.assertTrue("Version for 'Proposal' failed to increment on flush directive", obj.getVersion() > currentVersion || !modified);        
    }    
    
    @Test    
    @Transactional    
    public void ProposalIntegrationTest.testMerge() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Proposal' failed to initialize correctly", dod.getRandomProposal());        
        java.lang.Long id = dod.getRandomProposal().getId();        
        org.junit.Assert.assertNotNull("Data on demand for 'Proposal' failed to provide an identifier", id);        
        org.jpatest.domain.Proposal obj = org.jpatest.domain.Proposal.findProposal(id);        
        org.junit.Assert.assertNotNull("Find method for 'Proposal' illegally returned null for id '" + id + "'", obj);        
        boolean modified =  dod.modifyProposal(obj);        
        java.lang.Integer currentVersion = obj.getVersion();        
        obj.merge();        
        obj.flush();        
        org.junit.Assert.assertTrue("Version for 'Proposal' failed to increment on merge and flush directive", obj.getVersion() > currentVersion || !modified);        
    }    
    
    @Test    
    @Transactional    
    public void ProposalIntegrationTest.testPersist() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Proposal' failed to initialize correctly", dod.getRandomProposal());        
        org.jpatest.domain.Proposal obj = dod.getNewTransientProposal(Integer.MAX_VALUE);        
        org.junit.Assert.assertNotNull("Data on demand for 'Proposal' failed to provide a new transient entity", obj);        
        org.junit.Assert.assertNull("Expected 'Proposal' identifier to be null", obj.getId());        
        obj.persist();        
        obj.flush();        
        org.junit.Assert.assertNotNull("Expected 'Proposal' identifier to no longer be null", obj.getId());        
    }    
    
    @Test    
    @Transactional    
    public void ProposalIntegrationTest.testRemove() {    
        org.junit.Assert.assertNotNull("Data on demand for 'Proposal' failed to initialize correctly", dod.getRandomProposal());        
        java.lang.Long id = dod.getRandomProposal().getId();        
        org.junit.Assert.assertNotNull("Data on demand for 'Proposal' failed to provide an identifier", id);        
        org.jpatest.domain.Proposal obj = org.jpatest.domain.Proposal.findProposal(id);        
        org.junit.Assert.assertNotNull("Find method for 'Proposal' illegally returned null for id '" + id + "'", obj);        
        obj.remove();        
        org.junit.Assert.assertNull("Failed to remove 'Proposal' with identifier '" + id + "'", org.jpatest.domain.Proposal.findProposal(id));        
    }    
    
}
