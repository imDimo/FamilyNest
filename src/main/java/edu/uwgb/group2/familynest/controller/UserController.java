package edu.uwgb.group2.familynest.controller;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.uwgb.group2.familynest.model.NestUser;
import edu.uwgb.group2.familynest.service.UserService;

@Controller
//@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	String getUsers(Model model) {
		model.addAttribute("allusers", userService.getAllUsers());
		return "get_users";
	}
	
	@GetMapping("/users/add")
	String addUser(Model model) {
		NestUser user = new NestUser();
		model.addAttribute("user", user);
		return "add_user";
	}
	
	@PostMapping("/users/save")
	String saveUser(@ModelAttribute("user") NestUser user) {
		userService.createUser(user);
		return "redirect:/users";
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
