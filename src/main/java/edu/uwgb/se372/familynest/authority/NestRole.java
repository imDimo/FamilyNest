package edu.uwgb.se372.familynest.authority;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.*;

@Entity
@Table(name="role")
public class NestRole implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="role_name", nullable=false, unique=true)
	private String name;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="roles_privileges",
			joinColumns=@JoinColumn(
					name="role_id", referencedColumnName="id"),
			inverseJoinColumns=@JoinColumn(
					name="privilege_id", referencedColumnName="id"))
	private Collection<NestPrivilege> privileges;
	
	public NestRole() {}
	public NestRole(String name) {
		this.name = name;
		this.privileges = new ArrayList<>();
	}
	public NestRole(String name, Collection<NestPrivilege> privileges) {
		this.name = name;
		this.privileges = privileges;
	}
	
	public Long getId() {
		return id;
	}

	public Collection<NestPrivilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Collection<NestPrivilege> privileges) {
		this.privileges = new ArrayList<>(privileges);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
