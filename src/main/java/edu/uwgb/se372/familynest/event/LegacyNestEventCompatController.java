package edu.uwgb.se372.familynest.event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.uwgb.se372.familynest.user.NestUser;
import edu.uwgb.se372.familynest.user.NestUserRepository;

/**
 * Compatibility layer for the legacy NestEventController endpoints.
 *
 * The old implementation used POST /events with action parameters. We keep those routes
 * working while persisting events using CalendarEventManagement.
 */
@Controller
public class LegacyNestEventCompatController {
	
	@Autowired
	private CalendarEventManagementService calendarEventService;
	
	@Autowired
	private NestUserRepository userRepository;
	
	private NestUser getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated()) {
			return userRepository.findByUsername(auth.getName());
		}
		return null;
	}
	
	@PostMapping(value="/events", params="action=add")
	public String addEvent() {
		return "redirect:/calendar-events/new";
	}
	
	@PostMapping(value="/events", params="action=save")
	public String saveEvent(@RequestParam("title") String title,
							@RequestParam("description") String description,
							@RequestParam(value="eventDateTime", required=false) String eventDateTime) {
		NestUser currentUser = getCurrentUser();
		if (currentUser == null) {
			return "redirect:/login";
		}
		
		CalendarEventManagement event = new CalendarEventManagement();
		event.setTitle(title);
		event.setDescription(description);
		event.setCreator(currentUser);
		
		if (eventDateTime != null && !eventDateTime.isEmpty()) {
			try {
				event.setEventDate(LocalDateTime.parse(eventDateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
			} catch (Exception e) {
				event.setEventDate(LocalDateTime.now());
			}
		} else {
			event.setEventDate(LocalDateTime.now());
		}
		
		calendarEventService.createEvent(event);
		return "redirect:/calendar";
	}
	
	@PostMapping(value="/events", params="action=delete")
	public String deleteEvent(@RequestParam("id") Long id) {
		CalendarEventManagement event = calendarEventService.getEventById(id);
		NestUser currentUser = getCurrentUser();
		
		if (event != null && currentUser != null && event.getCreator() != null
				&& event.getCreator().getId().equals(currentUser.getId())) {
			calendarEventService.deleteEvent(id);
		}
		
		return "redirect:/calendar";
	}
}

