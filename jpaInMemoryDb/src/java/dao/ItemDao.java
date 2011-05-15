package dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Item;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ItemDao implements IItemDao {

    @PersistenceContext(unitName="testUnit")
    private EntityManager entityManager; 

    //private Object lock = new Object();
    
    @Transactional
    public Item add( Item item ) {
    
    	entityManager.persist( item );
    	return item;
    }
        
    public Item find( long id ) {
    	return entityManager.find( Item.class, id );
    }
    
    public List<Item> getAll() {
    	return entityManager.createQuery("SELECT o FROM Item o").getResultList();  
    }

	@Override
    @Transactional
	public synchronized Item setProperty1(Item item) {
		
		System.out.println( item + " start update at " + (new Date()).getTime() );
		
		Item dbItem = find( item.getId() );
		dbItem.setProperty1( item.getProperty1() );
		
		dbItem = entityManager.merge( dbItem );
		entityManager.flush();
		
		try {
			Thread.sleep(3000);
		} catch( Exception e ) {}

		System.out.println( item + " end update at " + (new Date()).getTime() );
		
		return dbItem;
	}

	@Override
    @Transactional
	public synchronized Item setProperty2(Item item) {
		
		System.out.println( item + " start update at " + (new Date()).getTime() );
		
		Item dbItem = find( item.getId() );
		dbItem.setProperty2( item.getProperty2() );
		
		dbItem = entityManager.merge( dbItem );
		entityManager.flush();
		
		try {
			Thread.sleep(3000);
		} catch( Exception e ) {}
		
		System.out.println( item + " end update at " + (new Date()).getTime() );

		return dbItem;
	}
}
