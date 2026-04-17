package edu.uwgb.se372.familynest.user;

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
	private boolean isAdmin;
	
	public NestUserDto() {}
	public NestUserDto(NestUser user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
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
	
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public boolean getIsAdmin() {
		return isAdmin;
	}
}
