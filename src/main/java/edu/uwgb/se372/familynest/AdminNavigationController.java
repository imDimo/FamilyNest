package edu.uwgb.se372.familynest;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.uwgb.se372.familynest.user.NestUser;
import edu.uwgb.se372.familynest.user.NestUserDto;
import edu.uwgb.se372.familynest.user.NestUserService;

@Controller
@RequestMapping("/admin")
public class AdminNavigationController {
	
	@Autowired
	NestUserService userService;
	
	@GetMapping("/manage-users")
	String manageUsers(Model model) {
		Collection<NestUserDto> allUsers = userService.getAllUsers().stream()
				.map((nestUser) -> new NestUserDto(nestUser)).collect(Collectors.toList());
		
		model.addAttribute("users", allUsers);
		
		return "/manage_users";
	}
	
	@PostMapping(value="/nav", params="action=manage-users")
	String postManageUsers(Model model) {
		return "redirect:/admin/manage-users";
	}
	
	@GetMapping("/add-user")
	String addUser(Model model) {
		NestUserDto user = new NestUserDto();
		model.addAttribute("user", user);
		return "/add_user";
	}
	
	@PostMapping(value="/nav", params="action=add-user")
	String postAddUser(Model model) {
		return "redirect:/admin/add-user";
	}
	
	@GetMapping("/manage-app")
	String manageApp(Model model) {
		return "/manage_app";
	}
	
	@PostMapping(value="/nav", params="action=manage-app")
	String postManageApp(Model model) {
		return "redirect:/admin/manage-app";
	}
}
