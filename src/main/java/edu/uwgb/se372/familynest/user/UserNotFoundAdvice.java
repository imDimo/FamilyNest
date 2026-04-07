package edu.uwgb.se372.familynest.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
class UserNotFoundAdvice {
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String userNotFoundHandler(UserNotFoundException e) {
		return e.getMessage();
	}
}
