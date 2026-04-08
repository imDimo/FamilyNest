package edu.uwgb.se372.familynest.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
class NestUserNotFoundAdvice {
	
	@ExceptionHandler(NestUserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String userNotFoundHandler(NestUserNotFoundException e) {
		return e.getMessage();
	}
}
