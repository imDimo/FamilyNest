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
	
	SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
	
	@GetMapping("/")
	String home() {
		return "index";
	}
	
	@GetMapping("/calendar")
	String calendar(Model model) {
		return "/calendar";
	}
	
	@PostMapping(value="/nav", params="action=calendar")
	String postCalendar(Model model) {
		return "redirect:/calendar";
	}
	
	@GetMapping("/gallery")
	String gallery(Model model) {
		return "redirect:/gallery";
	}
	
	@PostMapping(value="/nav", params="action=gallery")
	String postGallery(Model model) {
		return "redirect:/gallery";
	}
	
	@GetMapping("/settings")
	String settings(Model model) {
		return "redirect:/settings";
	}
	
	@PostMapping(value="/nav", params="action=settings")
	String postSettings(Model model) {
		return "redirect:/settings";
	}
	
	@PostMapping(value="/nav", params="action=logout")
	String postLogout(Authentication auth, HttpServletRequest request, HttpServletResponse response) {
		this.logoutHandler.logout(request, response, auth);
		return "redirect:/login";
	}

	public static void main(String[] args) {
		SpringApplication.run(FamilyNestApplication.class, args);
	}
}
