package rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameService implements INameService {
	
	private static Logger logger = LoggerFactory.getLogger(NameService.class);
	
	private volatile boolean plus = true;

	@Override
	public String getName(int id) {
		logger.debug( "Received request {} tid-{}", id, Thread.currentThread().getId() );
		
		try {
			long duration = 2000;
			if( plus ) {
				duration += 1000;
				plus = false;
			} else {
				duration -= 1000;
				plus = true;
			}
			Thread.sleep( duration );
		} catch(InterruptedException e) {			
		}
		
		logger.debug( "Returning request {}", id );
		return "Name-of-" + id;
	}

}
