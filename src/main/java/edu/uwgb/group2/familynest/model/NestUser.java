package edu.uwgb.group2.familynest.model;


import jakarta.persistence.*;

@Entity
@Table(name="USER")
public class NestUser {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="username", nullable=false)
	private String username;
	
	@Column(name="password_hash", nullable=false)
	private String passwordHash;
	
	
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

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
}

