package edu.uwgb.se372.familynest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
@SpringBootApplication
public class FamilyNestApplication {
	
	private SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
	
	@PostMapping(value="/nav", params="action=logout")
	public String postLogout(Authentication auth, HttpServletRequest request, HttpServletResponse response) {
		logoutHandler.logout(request, response, auth);
		return "redirect:/login";
	}

	public static void main(String[] args) {
		SpringApplication.run(FamilyNestApplication.class, args);
	}
}
