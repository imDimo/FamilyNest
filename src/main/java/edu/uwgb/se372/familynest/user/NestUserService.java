package edu.uwgb.se372.familynest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
	
	public String create(String username, String password, String authorities) {
		NestUser user = new NestUser(
			username,
			passwordEncoder.encode(password),
			"user"
		);
				
		return "Successfully created user";
	}
	
	public NestUser updateUser(Long userId, NestUser user) {
		NestUser existingUser = userRepository.findById(userId).orElse(null);
		if (existingUser != null) {
			existingUser.setUsername(user.getUsername());
			existingUser.setPassword(user.getPassword());
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
