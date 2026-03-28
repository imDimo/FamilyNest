package edu.uwgb.group2.familynest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uwgb.group2.familynest.db.NestUserRepository;
import edu.uwgb.group2.familynest.model.NestUser;

@Service
public class UserService {
	
	@Autowired
	private NestUserRepository userRepository;
	
	public List<NestUser> getAllUsers() {
		return userRepository.findAll();
	}
	
	public NestUser getUserById(Long userId) {
		return userRepository.findById(userId).orElse(null);
	}
	
	public NestUser createUser(NestUser user) {
		return userRepository.save(user);
	}
	
	public NestUser updateUser(Long userId, NestUser user) {
		NestUser existingUser = userRepository.findById(userId).orElse(null);
		if (existingUser != null) {
			existingUser.setUsername(user.getUsername());
			existingUser.setPasswordPasswordHash(user.getPasswordHash());
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
