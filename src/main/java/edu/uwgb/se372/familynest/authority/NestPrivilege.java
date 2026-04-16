package edu.uwgb.se372.familynest.authority;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.*;

@Entity
public class NestPrivilege {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="privilege_name", nullable=false, unique=true)
	private String name;
	
	@ManyToMany(mappedBy="privileges", fetch=FetchType.EAGER)
	private Collection<NestRole> roles;
	
	public NestPrivilege(String name) {
		this.name = name;
		this.roles = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
