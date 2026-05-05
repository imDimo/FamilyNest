package edu.uwgb.se372.familynest.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import edu.uwgb.se372.familynest.user.NestUser;

@Controller
@RequestMapping("/settings")
public class NestUserSettingsController {
	
	@Autowired
    private NestUserSettingsService userSettingsService;
	
	@PostMapping("/save")
	public String saveSettings(@AuthenticationPrincipal NestUser user, 
        @ModelAttribute("nestUserSettings") NestUserSettingsDto settingsDto) {
    
			NestUserSettings settings = user.getUserSettings();
			settings.setDarkMode(settingsDto.getDarkMode());
			settings.setAllowAnnouncements(settingsDto.getAllowAnnouncements());
			settings.setShowOnlineStatus(settingsDto.getShowOnlineStatus());
			userSettingsService.updateSettings(settings.getId(), settings);
			return "redirect:/settings";
		}
	
		@RequestMapping
		public String showSettings(@AuthenticationPrincipal NestUser user, Model model) {
			model.addAttribute("nestUserSettings", user.getUserSettings());
			return "settings";
		}
}
