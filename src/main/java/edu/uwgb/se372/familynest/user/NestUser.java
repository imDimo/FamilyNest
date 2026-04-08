package edu.uwgb.se372.familynest.user;

import lombok.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

@Entity
@Table(name="USER")
public class NestUser implements UserDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="username", nullable=false)
	private String username;
	
	//@Column(name="password", nullable=false)
	private String password;
	
	private String authorities;
	
	public NestUser() {};
	public NestUser(String username, String password, String authorities) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.stream(this.authorities.split("::"))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}
}

