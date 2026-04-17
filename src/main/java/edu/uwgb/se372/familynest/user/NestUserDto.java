package edu.uwgb.se372.familynest.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class NestUserDto {
	private Long id;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> roles;
	
	public NestUserDto() {}
	public NestUserDto(NestUser user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.roles = user.getAuthorities();
	}
	
	public Long getId() {
		return id;
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
