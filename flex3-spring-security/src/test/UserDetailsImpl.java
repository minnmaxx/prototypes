package test;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private static final Collection<GrantedAuthority> authorities;
	
	static {
		authorities = new ArrayList<GrantedAuthority>();
		authorities.add( new GrantedAuthorityImpl("ROLE_chatclient") );
	}
	
	private String username;
	private String password;
	
	public UserDetailsImpl( String username, String password ) {
		this.username = username;
		this.password = password;
	}
	
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
