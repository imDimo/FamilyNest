package edu.uwgb.se372.familynest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import edu.uwgb.se372.familynest.authority.NestRoleService;
import edu.uwgb.se372.familynest.user.NestUser;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.List;
import java.util.stream.IntStream;

@Controller
public class UserNavigationController {
    
    @Autowired
    private NestRoleService roleService;

    @GetMapping("/")
    public String home() {
        return "redirect:/calendar";
    }

    @GetMapping("/calendar")
    public String calendar(@AuthenticationPrincipal NestUser currentUser, Model model) {
    	boolean isAdmin = currentUser.hasRole(roleService.findByName("ROLE_ADMIN"));
    	model.addAttribute("userIsAdmin", isAdmin);
    	
        LocalDate today = LocalDate.now();
        YearMonth yearMonth = YearMonth.from(today);
        LocalDate startDay = yearMonth.atDay(1);

        String monthName = yearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.US);
        int year = yearMonth.getYear();
        int daysInMonth = yearMonth.lengthOfMonth();

        model.addAttribute("monthName", monthName);
        model.addAttribute("year", year);
        model.addAttribute("numDays", daysInMonth);
        model.addAttribute("startDayOffset", startDay.getDayOfWeek().getValue());
        model.addAttribute("currentDay", today.getDayOfMonth());
      
        return "/calendar";
    }

    @PostMapping(value = "/nav", params = "action=calendar")
    public String postCalendar(Model model) {
        return "redirect:/calendar";
    }

    @GetMapping("/settings")
    public String settings(@AuthenticationPrincipal NestUser currentUser, Model model) {
    	boolean isAdmin = currentUser.hasRole(roleService.findByName("ROLE_ADMIN"));
    	model.addAttribute("userIsAdmin", isAdmin);
    	
        return "/settings";
    }

    @PostMapping(value = "/nav", params = "action=settings")
    public String postSettings(Model model) {
        return "redirect:/settings";
    }
    
    @GetMapping(value = "/error")
    public String error(@AuthenticationPrincipal NestUser currentUser, HttpServletRequest request, Model model) {
    	
    	boolean isAdmin = false;
    	if (currentUser != null)
    		isAdmin = currentUser.hasRole(roleService.findByName("ROLE_ADMIN"));
    	
    	model.addAttribute("userIsAdmin", isAdmin);
    	
    	String message = null;
    	Object err = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    	
    	if (err != null) {
    		int code = Integer.valueOf(err.toString());
    		if (code == HttpStatus.NOT_FOUND.value()) {
    			message = "Error 404: Page Not Found";
    		}
    		else {
    			message = String.format("Error %d", code);
    		}
    	}
    	
    	model.addAttribute("error_code", message);
    	
    	return "/error";
    }
    
    @PostMapping(value = "/error")
    public String postError(Model model) {
        return "redirect:/error";
    }
}
