package edu.uwgb.se372.familynest.security;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import edu.uwgb.se372.familynest.user.NestUser;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private DataSource dataSource;

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
//				.requestMatchers("/admin/**").hasRole("admin")
//				.requestMatchers("/css/**", "/js/**", "/images/**", "/login**").permitAll()
				.requestMatchers("/**").permitAll()
				.anyRequest().authenticated())
		.formLogin(login -> login.loginPage("/login")
				.loginProcessingUrl("/login/process")
				.defaultSuccessUrl("/calendar", true));
		
		return http.build();
	}
	
	@Bean PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
