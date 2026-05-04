package edu.uwgb.se372.familynest.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NestEventController {

	@Autowired
	private NestEventService nestEventService;
	
	@PostMapping(value="/events", params="action=add")
	public String addEvent(Model model) {
		NestEvent event = new NestEvent();
		model.addAttribute("event", event);
		return "add_event";
	}
	
	@PostMapping(value="/events", params="action=save")
	public String saveEvent(@ModelAttribute("event") NestEvent event) {
		nestEventService.createEvent(event);
		return "redirect:/calendar";
	}
	
	@PostMapping(value="/events", params="action=delete")
	public String deleteEventById(@ModelAttribute("event") NestEvent event) {
		nestEventService.deleteEventById(event.getId());
		return "redirect:/calendar";
	}
}
