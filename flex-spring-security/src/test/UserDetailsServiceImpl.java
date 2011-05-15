package test;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

	private Map<String,UserDetails> users = new HashMap<String,UserDetails>();
	
	@PostConstruct
	private void init() {
		addUser( "cody", "c" );
		addUser( "ariss", "a" );
		addUser( "tangent", "t" );
	}
	
	private void addUser( String username, String password ) {
		UserDetailsImpl user = new UserDetailsImpl(username, password);
		users.put(username, user);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		
		UserDetails user = users.get( username );
		
		if( user == null ) {
			throw new UsernameNotFoundException( "user (" + username + ") does not exist." );
		}
		
		return user;
	}
}
