package edu.uwgb.se372.familynest.event;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.uwgb.se372.familynest.user.NestUser;
import edu.uwgb.se372.familynest.user.NestUserService;

@Controller
@RequestMapping("/events")
public class NestEventController {
	
	@Autowired
	private NestEventService eventService;
	
	@Autowired
	private NestUserService userService;
	
	@GetMapping("/{month}/{year}")
	@ResponseBody
	public List<NestEventDto> getEvents(@PathVariable Map<String, String> pathVariables) {
		
		try {
			int month = Integer.parseInt(pathVariables.get("month"));
			int year = Integer.parseInt(pathVariables.get("year"));
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("L d, y");
			
			List<NestEventDto> events = eventService.getEventsByMonthYear(month, year)
					.stream().map((event) -> {
						NestEventDto eventData = new NestEventDto(event);
						
						// Format string for JSON storage
						eventData.setEventDate(event.getEventDate().format(formatter));
						return eventData;
					}
				).toList();
			
			return events;
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return null;
	}
	
	@PostMapping("/create")
	public String createEvent(@AuthenticationPrincipal NestUser currentUser, @ModelAttribute("eventDto") NestEventDto eventData) {
		
		NestEvent event = new NestEvent();
		
		if (eventData.getTitle() == null || eventData.getTitle().isBlank()) {
			return "redirect:/error";
		}
		
		if (eventData.getEventDate() == null || eventData.getEventDate().isBlank()) {
			return "redirect:/error";
		}
		
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
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("y-LL-dd");
				event.setEventDate(LocalDate.parse(eventData.getEventDate().trim(), formatter));
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				event.setEventDate(LocalDate.now());
			}
		}
		else {
			event.setEventDate(LocalDate.now());
		}
		
		if (eventData.getEventTime() != null && !eventData.getEventTime().isEmpty()) {
			event.setEventTime(eventData.getEventTime());
		}
		else {
			event.setEventTime("All Day");
		}
		
		eventService.create(event);
		
		return "redirect:/calendar";
	}
	
	@PostMapping("/update")
	public String updateEvent(@AuthenticationPrincipal NestUser currentUser, Model model,
			@RequestBody NestEventDto eventData) {
		
		NestEvent existingEvent = null;
		NestEventDto existingEventData = null;
		
		try {
			existingEvent = eventService.getEventById(eventData.getId());
			existingEventData = new NestEventDto(existingEvent);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
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
		else {
			existingEventData.setEventTime("All Day");
		}
		
		eventService.updateEvent(eventData.getId(), existingEventData);
		
		return "redirect:/calendar";
	}
	
	@PostMapping("/delete/{id}")
	@ResponseBody
	public void deleteEventById(@AuthenticationPrincipal NestUser currentUser, @PathVariable("id") Long id) {
		NestEvent event = eventService.getEventById(id);
		
		if (event != null && currentUser != null && event.getCreator() != null
				&& event.getCreator().getId().equals(currentUser.getId())) {
			eventService.deleteEvent(id);
		}
	}
}
