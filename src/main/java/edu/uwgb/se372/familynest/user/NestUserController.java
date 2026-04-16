package edu.uwgb.se372.familynest.user;

import java.sql.SQLException;
import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.uwgb.se372.familynest.authority.NestRoleRepository;
import edu.uwgb.se372.familynest.authority.NestRoleService;

@Controller
@RequestMapping("/admin/users")
public class NestUserController {
	
	@Autowired
	private NestUserService userService;
	
	@Autowired
	private NestRoleService roleService;
	
	@Autowired
	private DataSource db;
	
	@GetMapping("/query")
	String getUsers() throws SQLException {
		var ps = db.getConnection().prepareStatement("SELECT * FROM user");
		var rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString("username"));
		}
		
		return "redirect:/calendar";
	}
	
	@GetMapping("/add")
	String addUser(Model model) {
		NestUserDto user = new NestUserDto();
		model.addAttribute("user", user);
		return "add_user";
	}
	
	@PostMapping("/save")
	String saveUser(@ModelAttribute("user") NestUserDto userData) {
		
		NestUser user = userService.create(
				userData.getUsername(), 
				userData.getPassword(), 
				Arrays.asList(roleService.findByName("ROLE_USER")));
		
		return "redirect:/login";
	}
	
	@GetMapping("/delete/{id}")
	String deleteUserById(@PathVariable(value="id") Long userId) {
		userService.deleteUserById(userId);
		return "redirect:/users";
	}
}
