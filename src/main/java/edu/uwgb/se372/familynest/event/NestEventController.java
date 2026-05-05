package edu.uwgb.se372.familynest.event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.uwgb.se372.familynest.user.NestUser;
import edu.uwgb.se372.familynest.user.NestUserRepository;
import edu.uwgb.se372.familynest.user.NestUserService;

@Controller
@RequestMapping("/calendar")
public class NestEventController {
	
	@Autowired
	private NestEventService eventService;
	
	@Autowired
	private NestUserService userService;
	
	@PostMapping("/events/create")
	public String createEvent(@AuthenticationPrincipal NestUser currentUser,
			Model model, @ModelAttribute("event") NestEventDto eventData) {
		NestEvent event = eventService.getEventById(eventData.getId());
		
		if (event != null) {
			return "redirect:/calendar";
		}
		
		event = new NestEvent();
		event.setTitle(eventData.getTitle());
		event.setDescription(eventData.getDescription());
		event.setCreator(currentUser);
		
		if (eventData.getMemberIds() != null && eventData.getMemberIds().size() > 0) {
			for (Long memberId : eventData.getMemberIds()) {
				event.addMember(userService.loadUserById(memberId));
			}
		}
		
		if (eventData.getEventDate() != null && !eventData.getEventDate().isEmpty()) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
				event.setEventDate(LocalDateTime.parse(eventData.getEventDate(), formatter));
			}
			catch (Exception e) {
				event.setEventDate(LocalDateTime.now());
			}
		}
		else {
			event.setEventDate(LocalDateTime.now());
		}
		
		eventService.create(event);
		
		return "redirect:/calendar";
	}
	
	@PostMapping("/events/update")
	public String updateEvent(@AuthenticationPrincipal NestUser currentUser, Model model,
			@ModelAttribute("event") NestEventDto eventData) {
		
		NestEvent existingEvent = null;
		NestEventDto existingEventData = null;
		
		try {
			existingEvent = eventService.getEventById(eventData.getId());
			existingEventData = new NestEventDto(existingEvent);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			return "redirect:/error";
		}
		
		if (eventData.getTitle() != null && !eventData.getTitle().isBlank()) {
			existingEventData.setTitle(eventData.getTitle());
		}
		else {
			existingEventData.setTitle("");
		}
		
		existingEventData.setDescription(eventData.getDescription());
		
		if (eventData.getEventDate() != null && !eventData.getEventDate().isBlank()) {
			existingEventData.setEventDate(eventData.getEventDate());
		}
		
		if (eventData.getCreatedAt() != null && !eventData.getCreatedAt().isBlank()) {
			existingEventData.setCreatedAt(eventData.getCreatedAt());
		}
		
		if (eventData.getEventTime() != null && !eventData.getEventTime().isBlank()) {
			existingEventData.setEventTime(eventData.getEventTime());
		}
		
		existingEvent.setUpdatedAt(LocalDateTime.now());
		
		eventService.updateEvent(eventData.getId(), existingEventData);
		
		return "redirect:/calendar";
	}
	
	@PostMapping("/events/delete")
	public String deleteEventById(@AuthenticationPrincipal NestUser currentUser, @RequestParam("id") Long id) {
		NestEvent event = eventService.getEventById(id);
		
		if (event != null && currentUser != null && event.getCreator() != null
				&& event.getCreator().getId().equals(currentUser.getId())) {
			eventService.deleteEvent(id);
		}
		
		return "redirect:/calendar";
	}
}
