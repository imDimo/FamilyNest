package edu.uwgb.se372.familynest.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NestUserDto {
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> roles;
	
	public NestUserDto() {};
	public NestUserDto(String username, String password, Collection<? extends GrantedAuthority> roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
	}
	public NestUserDto(NestUser user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.roles = user.getAuthorities();
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Collection<? extends GrantedAuthority> getRoles() {
		return roles;
	}
}
