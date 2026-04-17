package edu.uwgb.se372.familynest.user;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.uwgb.se372.familynest.authority.NestRoleService;

@Controller
@RequestMapping("/admin")
public class NestUserController {
	
	@Autowired
	private NestUserService userService;
	
	@Autowired
	private NestRoleService roleService;
	
	@PostMapping(value="/users", params="action=create")
	String addUser(Model model) {
		NestUserDto user = new NestUserDto();
		model.addAttribute("user", user);
		return "manage_users";
	}
	
	@PostMapping(value="/users", params="action=save")
	String saveUser(@ModelAttribute("user") NestUserDto userData) {
		
		NestUser user = userService.create(
				userData.getUsername(), 
				userData.getPassword(), 
				Arrays.asList(roleService.findByName("ROLE_USER")));
		
		return "manage_users";
	}
	
	@PostMapping(value="/users", params="action=delete")
	String deleteUserById(@PathVariable(value="id") Long userId) {
		userService.deleteUserById(userId);
		return "manage_users";
	}
}
