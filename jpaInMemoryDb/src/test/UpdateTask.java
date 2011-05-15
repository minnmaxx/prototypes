import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import model.Item;
import dao.IItemDao;


public abstract class UpdateTask implements Runnable {

	IItemDao dao;
	CountDownLatch latch;
	long id;
	Item result;
	UpdateTask( IItemDao dao, long id, CountDownLatch latch ) {
		this.dao = dao;
		this.id = id;
		this.latch = latch;
	}
	
	@Override
	public void run() {

		Item item = dao.find( id );
		System.out.println( item.hashCode() );
		latch.countDown();
		try {
			latch.await(10,TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println( "trying to update at " + (new Date()).getTime() );
		result = editItem( item );
	}
	
	abstract Item editItem( Item item );
}
