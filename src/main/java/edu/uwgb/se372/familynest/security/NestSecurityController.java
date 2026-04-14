package edu.uwgb.se372.familynest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import edu.uwgb.se372.familynest.user.NestUser;
import edu.uwgb.se372.familynest.user.NestUserService;

@Controller
public class NestSecurityController {
	
	@Autowired
	private NestUserService userService;
	
	@GetMapping("/login")
	String login(Model model) {
		return "login";
	}
	
	@PostMapping("/login/process")
	public String loginUser(/*@RequestBody NestUserInfoDto u*/) {
//		try {
//			NestUser user = userService.loadUserByUsername(u.getUsername());
//			System.out.println("Found user " + user.getUsername());
//		}
//		catch (UsernameNotFoundException e) {
//			System.out.printf("User '%s' was not found\n", e.getName());
//			return "redirect:/login";
//		}
		
		System.out.println("login request received");
		
		return "redirect:/calendar";
	}
}