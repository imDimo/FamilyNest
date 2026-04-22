package edu.uwgb.se372.familynest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import edu.uwgb.se372.familynest.user.NestUserService;


import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.List;
import java.util.stream.IntStream;

@Controller
public class UserNavigationController {

    @Autowired
    private NestUserService userService;

    @GetMapping("/")
    public String home() {
        return "redirect:/calendar";
    }

    @GetMapping("/calendar")
    public String calendar(Model model) {

      
        LocalDate today = LocalDate.now();
        YearMonth yearMonth = YearMonth.from(today);

        String monthName = today.getMonth().getDisplayName(TextStyle.FULL, Locale.US);
        int year = today.getYear();
        int daysInMonth = yearMonth.lengthOfMonth();

        List < Integer > days = IntStream.rangeClosed(1, daysInMonth).boxed().toList();

        model.addAttribute("monthName", monthName);
        model.addAttribute("year", year);
        model.addAttribute("days", days);
      
        return "/calendar";
    }

    @PostMapping(value = "/nav", params = "action=calendar")
    public String postCalendar(Model model) {
        return "redirect:/calendar";
    }

    @GetMapping("/gallery")
    public String gallery(Model model) {
        return "/gallery";
    }

    @PostMapping(value = "/nav", params = "action=gallery")
    public String postGallery(Model model) {
        return "redirect:/gallery";
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        return "/settings";
    }

    @PostMapping(value = "/nav", params = "action=settings")
    public String postSettings(Model model) {
        return "redirect:/settings";
    }
}
