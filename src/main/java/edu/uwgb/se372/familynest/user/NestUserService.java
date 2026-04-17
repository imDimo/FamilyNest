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
	
	public NestUser loadUserById(Long id) {
		NestUser user = userRepository.findById(id).orElse(null);
		if (user == null) {
			throw new NestUserNotFoundException(id);
		}
		return user;
	}
	
	public List<NestUser> getAllUsers() {
		return userRepository.findAll();
	}
	
	public List<NestUser> findUsersWithRoles(List<NestRole> roles) {
		
		return userRepository.findByRolesIn(roles);
	}
	
	public NestUser create(String username, String password, List<NestRole> roles) {
		
		NestUser user = userRepository.findByUsername(username);
		
		if (user == null) {
			user = new NestUser(
				username,
				passwordEncoder.encode(password),
				roles
			);
			user = userRepository.save(user);
		}
		
		return user;
	}
	
	public NestUser updateUser(Long userId, NestUser userdata) {
		NestUser existingUser = userRepository.findById(userId).orElse(null);
		if (existingUser != null) {
			existingUser.setUsername(userdata.getUsername());
			existingUser.setPassword(passwordEncoder.encode(userdata.getPassword()));
			return userRepository.save(existingUser);
		}
		else {
			return null;
		}
	}
	
	public void deleteUserById(Long userId) {
		userRepository.deleteById(userId);
	}
}
