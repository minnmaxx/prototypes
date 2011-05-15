package services;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class AccountTokenService implements IAccountTokenService {

	private Map<String,String> emailToToken = new HashMap<String,String>();
	private AtomicLong tokenCounter = new AtomicLong(10000);
	
	@Override
	public synchronized boolean invaidateToken(String email, String token) {
		
		String tokenOnRecord = emailToToken.get( email );
		if( tokenOnRecord == null || !tokenOnRecord.equals(token) ) {
			return false;
		}				
		emailToToken.remove(email);
		return true;
	}

	@Override
	public synchronized String newToken(String email) {

		String token = Long.toString( tokenCounter.getAndIncrement() );
		emailToToken.put(email, token);
		
		return token;
	}
}
