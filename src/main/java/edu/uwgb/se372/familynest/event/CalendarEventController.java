package edu.uwgb.se372.familynest.event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

@Controller
@RequestMapping("/calendar-events")
public class CalendarEventController {
	
	@Autowired
	private CalendarEventManagementService calendarEventService;
	
	@Autowired
	private NestUserRepository userRepository;
	
	// Get current authenticated user
	private NestUser getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated()) {
			String username = auth.getName();
			return userRepository.findByUsername(username);
		}
		return null;
	}
	
	// Display all calendar events
	@GetMapping
	public String listCalendarEvents(Model model) {
		NestUser currentUser = getCurrentUser();
		if (currentUser != null) {
			List<CalendarEventManagement> userEvents = calendarEventService.getUserEvents(currentUser.getId());
			model.addAttribute("events", userEvents);
		} else {
			model.addAttribute("events", calendarEventService.getAllEvents());
		}
		return "calendar_events";
	}
	
	// Show create event form
	@GetMapping("/new")
	public String showCreateEventForm(Model model) {
		model.addAttribute("event", new CalendarEventManagement());
		List<NestUser> allUsers = userRepository.findAll();
		model.addAttribute("allUsers", allUsers);
		return "create_calendar_event";
	}
	
	// Create new event
	@PostMapping("/save")
	public String saveCalendarEvent(@ModelAttribute CalendarEventManagement event,
									 @RequestParam(value="eventDateTime", required=false) String eventDateTime,
									 @RequestParam(value="memberIds", required=false) Long[] memberIds) {
		NestUser currentUser = getCurrentUser();
		
		if (currentUser != null) {
			event.setCreator(currentUser);
			
			// Parse the date-time if provided
			if (eventDateTime != null && !eventDateTime.isEmpty()) {
				try {
					DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
					event.setEventDate(LocalDateTime.parse(eventDateTime, formatter));
				} catch (Exception e) {
					event.setEventDate(LocalDateTime.now());
				}
			}
			
			// Add members if provided
			if (memberIds != null && memberIds.length > 0) {
				Set<Long> memberIdSet = new HashSet<>();
				for (Long memberId : memberIds) {
					memberIdSet.add(memberId);
				}
				CalendarEventManagement savedEvent = calendarEventService.createEvent(event);
				calendarEventService.addMembersToEvent(savedEvent.getId(), memberIdSet);
				return "redirect:/calendar-events";
			}
			
			calendarEventService.createEvent(event);
		}
		
		return "redirect:/calendar-events";
	}
	
	// Show edit event form
	@GetMapping("/edit/{id}")
	public String showEditEventForm(@PathVariable Long id, Model model) {
		CalendarEventManagement event = calendarEventService.getEventById(id);
		NestUser currentUser = getCurrentUser();
		
		// Only allow creator to edit
		if (event != null && event.getCreator().getId().equals(currentUser.getId())) {
			model.addAttribute("event", event);
			List<NestUser> allUsers = userRepository.findAll();
			model.addAttribute("allUsers", allUsers);
			model.addAttribute("selectedMembers", event.getMembers());
			return "edit_calendar_event";
		}
		
		return "redirect:/calendar-events";
	}
	
	// Update event
	@PostMapping("/{id}/update")
	public String updateCalendarEvent(@PathVariable Long id,
									   @ModelAttribute CalendarEventManagement eventDetails,
									   @RequestParam(value="eventDateTime", required=false) String eventDateTime,
									   @RequestParam(value="memberIds", required=false) Long[] memberIds) {
		CalendarEventManagement existingEvent = calendarEventService.getEventById(id);
		NestUser currentUser = getCurrentUser();
		
		// Only allow creator to update
		if (existingEvent != null && existingEvent.getCreator().getId().equals(currentUser.getId())) {
			existingEvent.setTitle(eventDetails.getTitle());
			existingEvent.setDescription(eventDetails.getDescription());
			existingEvent.setEventTime(eventDetails.getEventTime());
			
			// Parse the date-time if provided
			if (eventDateTime != null && !eventDateTime.isEmpty()) {
				try {
					DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
					existingEvent.setEventDate(LocalDateTime.parse(eventDateTime, formatter));
				} catch (Exception e) {
					// Keep existing date if parsing fails
				}
			}
			
			// Update members
			if (memberIds != null) {
				existingEvent.getMembers().clear();
				for (Long memberId : memberIds) {
					NestUser member = userRepository.findById(memberId).orElse(null);
					if (member != null) {
						existingEvent.addMember(member);
					}
				}
			}
			
			calendarEventService.updateEvent(id, existingEvent);
		}
		
		return "redirect:/calendar-events";
	}
	
	// Delete event
	@GetMapping("/{id}/delete")
	public String deleteCalendarEvent(@PathVariable Long id) {
		CalendarEventManagement event = calendarEventService.getEventById(id);
		NestUser currentUser = getCurrentUser();
		
		// Only allow creator to delete
		if (event != null && event.getCreator().getId().equals(currentUser.getId())) {
			calendarEventService.deleteEvent(id);
		}
		
		return "redirect:/calendar-events";
	}
	
	// View event details
	@GetMapping("/{id}")
	public String viewEventDetails(@PathVariable Long id, Model model) {
		CalendarEventManagement event = calendarEventService.getEventById(id);
		if (event != null) {
			model.addAttribute("event", event);
			model.addAttribute("creator", event.getCreator());
			model.addAttribute("members", event.getMembers());
			return "event_details";
		}
		return "redirect:/calendar-events";
	}
	
	// Add member to existing event
	@PostMapping("/{eventId}/add-member/{userId}")
	public String addMemberToEvent(@PathVariable Long eventId, @PathVariable Long userId) {
		CalendarEventManagement event = calendarEventService.getEventById(eventId);
		NestUser currentUser = getCurrentUser();
		
		// Only allow creator to add members
		if (event != null && event.getCreator().getId().equals(currentUser.getId())) {
			calendarEventService.addMemberToEvent(eventId, userId);
		}
		
		return "redirect:/calendar-events/" + eventId;
	}
	
	// Remove member from event
	@GetMapping("/{eventId}/remove-member/{userId}")
	public String removeMemberFromEvent(@PathVariable Long eventId, @PathVariable Long userId) {
		CalendarEventManagement event = calendarEventService.getEventById(eventId);
		NestUser currentUser = getCurrentUser();
		
		// Only allow creator to remove members
		if (event != null && event.getCreator().getId().equals(currentUser.getId())) {
			calendarEventService.removeMemberFromEvent(eventId, userId);
		}
		
		return "redirect:/calendar-events/" + eventId;
	}
}
