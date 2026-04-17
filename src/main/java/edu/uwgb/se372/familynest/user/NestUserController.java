package edu.uwgb.se372.familynest.user;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.uwgb.se372.familynest.authority.NestRoleService;

@Controller
@RequestMapping("/admin")
public class NestUserController {
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	private NestUserService userService;
	
	@Autowired
	private NestRoleService roleService;
	
	@PostMapping("/users/create")
	String createUser(Model model, @ModelAttribute("user") NestUserDto userData) {
		NestUser user = null;
		
		try {
			user = userService.loadUserByUsername(userData.getUsername());
		}
		catch (Exception e) {
			user = userService.create(
				userData.getUsername(), 
				userData.getPassword(), 
				Arrays.asList(roleService.findByName("ROLE_USER")));
		}
		
		return "redirect:/admin/manage-users";
	}
	
	@PostMapping(value="/users", params="action=update-user")
	String updateUser(@ModelAttribute("user") NestUserDto userData) {
		// TODO: Update user with incoming data
		return "redirect:/admin/manage-users";
	}
	
	@PostMapping(value="/users", params="action=delete-user")
	String deleteUserById(@ModelAttribute(value="id") Long userId) {
		userService.deleteUserById(userId);
		return "redirect:/admin/manage-users";
	}
}
