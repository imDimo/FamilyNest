package edu.uwgb.se372.familynest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class NestPasswordEncoder {
	@Bean PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
