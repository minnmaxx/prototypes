package rpc;

import java.util.concurrent.atomic.AtomicInteger;

public class IdService implements IIdService {

	private AtomicInteger id;
	
	public IdService( int id ) {
		this.id = new AtomicInteger( id );
	}

	@Override
	public int getId(String name) {
		return id.getAndIncrement();
	}
}
