package services;

public interface IAccountTokenService {

	/**
	 * Create and return a new token for the input email.  
	 * Invalidate all existing tokens for the email.
	 * @param email
	 * @return
	 */
	String newToken( String email );
	
	/**
	 * Searches for the pair of email and token.  If not, invalidate
	 * the token, and returns true.  If not found, returns false.
	 * @param email
	 * @param token
	 * @return
	 */
	boolean invaidateToken( String email, String token );
}

