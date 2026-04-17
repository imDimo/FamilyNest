package edu.uwgb.se372.familynest.user;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.uwgb.se372.familynest.authority.NestRoleService;

@Controller
@RequestMapping("/admin/users")
public class NestUserController {
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	private NestUserService userService;
	
	@Autowired
	private NestRoleService roleService;
	
	@PostMapping("/create")
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
			
			System.out.println("Created user with");
			System.out.println(user.getUsername());
			System.out.println(user.getPassword());
		}
		
		return "redirect:/admin/manage-users";
	}
	
	@PostMapping("/update")
	String updateUser(@ModelAttribute("user") NestUserDto userData) {
		// TODO: Update user with incoming data
		return "redirect:/admin/manage-users";
	}
	
	@PostMapping("/delete")
	String deleteUserById(@PathVariable(value="id") Long userId) {
		userService.deleteUserById(userId);
		return "redirect:/admin/manage-users";
	}
}
