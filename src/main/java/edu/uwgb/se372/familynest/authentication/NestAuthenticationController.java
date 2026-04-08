package edu.uwgb.se372.familynest.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import edu.uwgb.se372.familynest.user.NestUser;
import edu.uwgb.se372.familynest.user.NestUserService;

@Controller
public class NestAuthenticationController {
	
	@Autowired
	private NestUserService userService;
	
	@GetMapping("/login")
	String login(Model model) {
		return "login";
	}
	
	@PostMapping("/login")
	public String loginUser(@RequestBody NestUserInfoDto u) {
		try {
			NestUser user = userService.loadUserByUsername(u.getUsername());
		}
		catch (UsernameNotFoundException e) {
			System.out.printf("User '%s' was not found\n", e.getName());
			return "redirect:/login";
		}
		
		return "redirect:/calendar";
	}
}