package edu.uwgb.se372.familynest.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import edu.uwgb.se372.familynest.user.NestUserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class NestSecurityController {
	
	@GetMapping("/login")
	public String login(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession(false);
		String message = null;
		
		if (session != null) {
			AuthenticationException ex = (AuthenticationException) session
					.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
			
			if (ex != null) {
				message = "Incorrect username or password";
				session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
			}
		}
		
		NestUserDto user = new NestUserDto();
		model.addAttribute("user", user);
		model.addAttribute("error", message);
		return "login";
	}
}