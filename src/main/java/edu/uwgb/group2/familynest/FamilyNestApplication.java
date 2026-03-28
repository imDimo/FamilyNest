package edu.uwgb.group2.familynest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@SpringBootApplication
public class FamilyNestApplication {
	
	/*
	@Autowired
	UserService userService;
	*/
	
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

}
