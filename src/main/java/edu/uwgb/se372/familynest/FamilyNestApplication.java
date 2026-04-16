package edu.uwgb.se372.familynest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@SpringBootApplication
public class FamilyNestApplication {
	
	@GetMapping("/")
	String home() {
		return "index";
	}
	
	@GetMapping("/calendar")
	String calendar(Model model) {
		return "calendar";
	}

	public static void main(String[] args) {
		SpringApplication.run(FamilyNestApplication.class, args);
	}
	
	// TODO: Make this work proprly
	// Creates a new admin user if one doesn't exist
//	@EventListener(ApplicationReadyEvent.class)
//	public void checkForAdmin() {
//		
//		List<NestUser> users = userService.getAllUsers();
//		
//		System.out.println("Users:");
//		for (NestUser u : users)
//			System.out.println(u.getUsername() + ": " + u.getAuthorities().toArray()[0]);
//		
//	    List<NestUser> admins = userService.findUsersWithRoles(List.of("user", "admin"));
//	    if (admins.isEmpty()) {
//	    	System.out.println("No admin users present! Creating default admin account...");
//	    	userService.create("admin", "password", "user::admin");
//	    }
//	    else {
//	    	
//	    	System.out.println("Admins:");
//	    	for (NestUser u : admins)
//	    		System.out.println(u.getUsername());
//	    }
//	}
}
