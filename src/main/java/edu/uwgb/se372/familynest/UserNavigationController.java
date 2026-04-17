package edu.uwgb.se372.familynest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import edu.uwgb.se372.familynest.user.NestUserService;

@Controller
public class UserNavigationController {
	
	@Autowired
	NestUserService userService;
	
	@GetMapping("/")
	String home() {
		return "redirect:/calendar";
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
		return "/gallery";
	}
	
	@PostMapping(value="/nav", params="action=gallery")
	String postGallery(Model model) {
		return "redirect:/gallery";
	}
	
	@GetMapping("/settings")
	String settings(Model model) {
		return "/settings";
	}
	
	@PostMapping(value="/nav", params="action=settings")
	String postSettings(Model model) {
		return "redirect:/settings";
	}
}
