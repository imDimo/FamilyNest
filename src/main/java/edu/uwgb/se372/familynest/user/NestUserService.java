package edu.uwgb.se372.familynest.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.uwgb.se372.familynest.authority.NestRole;

@Service
public class NestUserService implements UserDetailsService {
	
	@Autowired
	private NestUserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public NestUser loadUserByUsername(String username) {
		
		NestUser user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return user;
	}
	
	public List<NestUser> getAllUsers() {
		return userRepository.findAll();
	}
	
	public List<NestUser> findUsersWithRoles(List<String> roles) {
		
		return userRepository.findByRolesIn(roles);
	}
	
	public NestUser create(String username, String password, List<NestRole> roles) {
		NestUser user = new NestUser(
			username,
			passwordEncoder.encode(password),
			roles
		);
		
		return userRepository.save(user);
	}
	
	public NestUser updateUser(Long userId, NestUser userdata) {
		NestUser existingUser = userRepository.findById(userId).orElse(null);
		if (existingUser != null) {
			existingUser.setUsername(userdata.getUsername());
			existingUser.setPassword(userdata.getPassword());
			return userRepository.save(existingUser);
		}
		else {
			return null;
		}
	}
	
	public void deleteUserById(Long userId) {
		userRepository.deleteById(userId);
	}
	
//	private Collection<? extends GrantedAuthority> getAuthorities(Collection<NestRole> roles) {
//		return getGrantedAuthorities(getPrivileges(roles));
//	}
//	
//	private List<String> getPrivileges(Collection<NestRole> roles) {
//		List<String> privilege_names = new ArrayList<>();
//		List<NestPrivilege> privileges = new ArrayList<>();
//		
//		for (NestRole role : roles) {
//			privilege_names.add(role.getName());
//			privileges.addAll(role.getPrivileges());
//		}
//		
//		for (NestPrivilege privilege : privileges)
//			privilege_names.add(privilege.getName());
//		
//		return privilege_names;
//	}
//	
//	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
//		List<GrantedAuthority> authorities = new ArrayList<>();
//		for (String privilege : privileges)
//			authorities.add(new SimpleGrantedAuthority(privilege));
//		
//		return authorities;
//	}
}
