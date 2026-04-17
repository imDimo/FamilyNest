package edu.uwgb.se372.familynest.user;

public class NestUserNotFoundException extends RuntimeException {
	public NestUserNotFoundException(Long id) {
		super(String.format("User with id %d does not exist", id));
	}
}
