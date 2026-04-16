package edu.uwgb.se372.familynest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import edu.uwgb.se372.familynest.user.NestUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private NestUserService userService;
	
//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userService);
//		provider.setPasswordEncoder(encoder);
//		return provider;
//	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests((auth) -> auth
				.requestMatchers("/admin/**").hasRole("admin")
				.requestMatchers("/css/**", "/js/**", "/images/**", "/login**").permitAll()
//				.requestMatchers("/**").permitAll()
				.anyRequest().authenticated())
		.formLogin(conf -> conf.loginPage("/login")
				.loginProcessingUrl("/login/process")
				.failureUrl("/login")
				.defaultSuccessUrl("/calendar", false))
		.logout((conf) -> conf
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				.permitAll());
		
		return http.build();
	}
}
