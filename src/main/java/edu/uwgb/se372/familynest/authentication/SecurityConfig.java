package edu.uwgb.se372.familynest.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	// TODO: Fix circular dependency here
//	@Autowired
//	private NestUserService userService;
//	
//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userService);
//		provider.setPasswordEncoder(encoder());
//		return provider;
//	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests((auth) -> auth
				.requestMatchers("/admin/**").hasRole("admin")
				.requestMatchers("/css/**", "/js/**", "/images/**", "/login**").permitAll()
				.anyRequest().authenticated())
		.formLogin(login -> login.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/calendar", true));
		
		return http.build();
	}
	
	@Bean PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
