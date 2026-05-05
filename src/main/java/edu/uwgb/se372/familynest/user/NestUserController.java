package edu.uwgb.se372.familynest.user;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.uwgb.se372.familynest.authority.NestRoleService;
import edu.uwgb.se372.familynest.settings.NestUserSettingsService;

@Controller
@RequestMapping("/admin")
public class NestUserController {
	
	@Autowired
	private NestUserService userService;
	
	@Autowired
	private NestUserSettingsService userSettingsService;
	
	@Autowired
	private NestRoleService roleService;
	
	@PostMapping("/users/create")
	public String createUser(Model model, @ModelAttribute("user") NestUserDto userData) {
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
						Arrays.asList(roleService.findByName("ROLE_USER"), roleService.findByName("ROLE_ADMIN")),
						userSettingsService.createSettings());
			}
			else {
				user = userService.create(username.trim(), password.trim(), 
						Arrays.asList(roleService.findByName("ROLE_USER")),
						userSettingsService.createSettings());
			}
		}
		
		return "redirect:/admin/manage-users";
	}
	
	@PostMapping("/users/update")
	public String updateUser(@AuthenticationPrincipal NestUser currentUser, Model model, 
			@ModelAttribute("user") NestUserDto userData) {
		NestUser existingUser = null;
		NestUserDto existingUserData = null;
		
		try {
			existingUserData = new NestUserDto(existingUser);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			return "redirect:/error";
		}
		
		if (userData.getUsername() != null && !userData.getUsername().isBlank()) {
			try {
				NestUser userWithName = userService.loadUserByUsername(userData.getUsername().trim());
				
				if (userWithName.getId() != existingUser.getId()) {
					// TODO: Add info message when username is already taken
					return "redirect:/admin/manage-users";
				}
			}
			catch (Exception e) {
				// Username not found, so we can update it
				System.out.printf("Changing username of user %d", userData.getId());
				existingUserData.setUsername(userData.getUsername().trim());
			}
		}
		else {
			existingUserData.setUsername("");
		}
		
		if (userData.getPassword() != null && !userData.getPassword().isBlank()) {
			existingUserData.setPassword(userData.getPassword().trim());
		}
		else {
			existingUserData.setPassword("");
		}
		
		existingUserData.setIsAdmin(userData.getIsAdmin());
		
		userService.updateUser(userData.getId(), existingUserData);
		
		// If user updated their own information, they will have to log back in
		if (userData.getId().equals(currentUser.getId()))
			return "redirect:/logout";
		
		return "redirect:/admin/manage-users";
	}
	
	@PostMapping(value="/users", params="action=delete-user")
	public String deleteUserById(@ModelAttribute(value="id") Long userId) {
		
		// TODO: Prevent users from deleting themselves
		try {
			NestUser user = userService.loadUserById(userId);
			
			userSettingsService.deleteSettingsById(user.getUserSettings().getId());
			userService.deleteUserById(userId);
		}
		catch (Exception e) {
			System.out.println("Attempted to remove a user but they did not exist");
		}
		
		return "redirect:/admin/manage-users";
	}
}
