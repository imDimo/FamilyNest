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
	
	@PostMapping("/users/create")
	String createUser(Model model, @ModelAttribute("user") NestUserDto userData) {
		NestUser user = null;
		
		try {
			user = userService.loadUserByUsername(userData.getUsername());
		}
		catch (Exception e) {
			String username = userData.getUsername();
			String password = userData.getPassword();
			if (username == null || password == null || username.isBlank() || password.isBlank()) {
				return "redirect:/admin/manage-users";
			}
			
			if (userData.getIsAdmin()) {
				user = userService.create(username.trim(), password.trim(), 
						Arrays.asList(roleService.findByName("ROLE_USER"), roleService.findByName("ROLE_ADMIN")));
			}
			else {
				user = userService.create(username.trim(), password.trim(), 
						Arrays.asList(roleService.findByName("ROLE_USER")));
			}
		}
		
		return "redirect:/admin/manage-users";
	}
	
	@PostMapping("/users/update")
	String updateUser(Model model, @ModelAttribute("user") NestUserDto userData) {
		NestUser user = null;
		
		try {
			user = userService.loadUserById(userData.getId());
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			return "redirect:/admin/manage-users";
		}
		
		// TODO: Add info messages when these operations fail
		if (userData.getUsername() == null || userData.getUsername().isBlank())
			return "redirect:/admin/manage-users";
		
		if (userData.getPassword() == null || userData.getPassword().isBlank())
			return "redirect:/admin/manage-users";
		
		user.setUsername(userData.getUsername().trim());
		user.setPassword(userData.getPassword().trim());
		
		if (userData.getIsAdmin()) {
			user.setRoles(Arrays.asList(roleService.findByName("ROLE_USER"), roleService.findByName("ROLE_ADMIN")));
		}
		else {
			user.setRoles(Arrays.asList(roleService.findByName("ROLE_USER")));
		}
		
		userService.updateUser(user.getId(), user);
		
		return "redirect:/admin/manage-users";
	}
	
	@PostMapping(value="/users", params="action=delete-user")
	String deleteUserById(@ModelAttribute(value="id") Long userId) {
		// TODO: Prevent users from deleting themselves
		userService.deleteUserById(userId);
		return "redirect:/admin/manage-users";
	}
}
