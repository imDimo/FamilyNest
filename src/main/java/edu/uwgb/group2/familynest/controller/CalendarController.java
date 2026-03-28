package edu.uwgb.group2.familynest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import edu.uwgb.group2.familynest.model.NestEvent;
import edu.uwgb.group2.familynest.service.NestEventService;

@Controller
public class CalendarController {

	@Autowired
	private NestEventService nestEventService;
	
	@GetMapping("/events")
	String getEvents(Model model) {
		model.addAttribute("allevents", nestEventService.getAllEvents());
		return "get_events";
	}
	
	@GetMapping("/events/add")
	String addEvent(Model model) {
		NestEvent event = new NestEvent();
		model.addAttribute("event", event);
		return "add_event";
	}
	
	@PostMapping("/events/save")
	String saveEvent(@ModelAttribute("event") NestEvent event) {
		nestEventService.createEvent(event);
		return "redirect:/calendar";
	}
	
	@GetMapping("/events/delete/{id}")
	String deleteEventById(@PathVariable(value="id") Long eventId) {
		nestEventService.deleteEventById(eventId);
		return "redirect:/calendar";
	}
}
