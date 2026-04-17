package edu.uwgb.se372.familynest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests((auth) -> auth
				.requestMatchers("/admin/**").hasRole("ADMIN")
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
