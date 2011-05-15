package test;

import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private static final GrantedAuthority[] authorities =
		new GrantedAuthority[] {
			new GrantedAuthorityImpl("ROLE_chatclient")
		};
	
	private String username;
	private String password;
	
	public UserDetailsImpl( String username, String password ) {
		this.username = username;
		this.password = password;
	}
	
	@Override
	public GrantedAuthority[] getAuthorities() {
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
