package edu.uwgb.se372.familynest.user;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(Long id) {
		super(String.format("User %d does not exist", id));
	}
}
