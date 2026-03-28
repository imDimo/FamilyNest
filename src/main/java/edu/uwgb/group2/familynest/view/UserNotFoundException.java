package edu.uwgb.group2.familynest.view;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(Long id) {
		super(String.format("User %d does not exist", id));
	}
}
