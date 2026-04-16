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
//@RequestMapping("/api/users")
public class NestUserController {
	
	@Autowired
	private NestUserService userService;
	
	@Autowired
	private NestRoleService roleService;
	
	@Autowired
	private DataSource db;
	
	@GetMapping("/users/query")
	String getUsers() throws SQLException {
		var ps = db.getConnection().prepareStatement("SELECT * FROM user");
		var rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println(rs.getString("username"));
		}
		
		return "redirect:/calendar";
	}
	
	@GetMapping("/users/add")
	String addUser(Model model) {
		NestUserDto user = new NestUserDto();
		model.addAttribute("user", user);
		return "add_user";
	}
	
	@PostMapping("/users/save")
	String saveUser(@ModelAttribute("user") NestUserDto userData) {
		
		NestUser user = userService.create(
				userData.getUsername(), 
				userData.getPassword(), 
				Arrays.asList(roleService.findByName("ROLE_USER")));
		
		return "redirect:/login";
	}
	
	@GetMapping("/users/delete/{id}")
	String deleteUserById(@PathVariable(value="id") Long userId) {
		userService.deleteUserById(userId);
		return "redirect:/users";
	}
	
	/*
	// POST /api/users
	@PostMapping
	public User createUser(@RequestBody User user) {
		return userService.createUser(user);
	}
	
	// GET /api/users
	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	// GET /api/users/{id}
	@GetMapping("/{id}")
	public User getUserByID(@PathVariable(value="id") Long userId) {
		return userService.getUserById(userId);
	}
	
	// PUT /api/users/{id}
	@PutMapping("/{id}")
	public User updateUser(
			@PathVariable(value="id") Long userId,
			@RequestBody User userDetails) {
		return userService.updateUser(userId, userDetails);
	}
	
	// DELETE /api/users/{id}
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable(value="id") Long userId) {
		userService.deleteUserById(userId);
	}
	*/
}
