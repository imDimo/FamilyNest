package edu.uwgb.se372.familynest.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import edu.uwgb.se372.familynest.authority.NestPrivilege;
import edu.uwgb.se372.familynest.authority.NestRole;
import jakarta.persistence.*;

@Entity
@Table(name="user")
public class NestUser implements UserDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="username", nullable=false, unique=true)
	private String username;
	
	@Column(name="password", nullable=false)
	private String password;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="users_roles", 
			joinColumns=@JoinColumn(
					name="user_id", referencedColumnName="id"),
			inverseJoinColumns = @JoinColumn(
					name="role_id", referencedColumnName="id"))
	private Collection<NestRole> roles;
	
	public NestUser() {};
	public NestUser(String username, String password) {
		this.username = username;
		this.password = password;
		this.roles = new ArrayList<NestRole>();
	}
	public NestUser(String username, String password, List<NestRole> roles) {
		this.username = username;
		this.password = password;
		this.roles = roles;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getGrantedAuthorities(getPrivileges());
	}
	
	private List<String> getPrivileges() {
		List<String> privilege_names = new ArrayList<>();
		List<NestPrivilege> privileges = new ArrayList<>();
		
		for (NestRole role : roles) {
			privilege_names.add(role.getName());
			privileges.addAll(role.getPrivileges());
		}
		
		for (NestPrivilege privilege : privileges)
			privilege_names.add(privilege.getName());
		
		return privilege_names;
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String privilege : privileges)
			authorities.add(new SimpleGrantedAuthority(privilege));
		
		return authorities;
	}
	
	/**
     * Indicates whether the user's account has expired.
     * @return true if the account is non-expired, false otherwise
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     * @return true if the account is non-locked, false otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials have expired.
     * @return true if the credentials are non-expired, false otherwise
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     * @return true if the user is enabled, false otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}

