package edu.uwgb.se372.familynest.event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uwgb.se372.familynest.user.NestUser;
import edu.uwgb.se372.familynest.user.NestUserRepository;

@Service
public class CalendarEventManagementService {
	
	@Autowired
	private CalendarEventRepository calendarEventRepository;
	
	@Autowired
	private NestUserRepository nestUserRepository;
	
	// Create event
	public CalendarEventManagement createEvent(CalendarEventManagement event) {
		event.setCreatedAt(LocalDateTime.now());
		event.setUpdatedAt(LocalDateTime.now());
		return calendarEventRepository.save(event);
	}
	
	// Get all events
	public List<CalendarEventManagement> getAllEvents() {
		return calendarEventRepository.findAll();
	}
	
	// Get event by ID
	public CalendarEventManagement getEventById(Long eventId) {
		return calendarEventRepository.findById(eventId).orElse(null);
	}
	
	// Update event
	public CalendarEventManagement updateEvent(Long eventId, CalendarEventManagement eventDetails) {
		CalendarEventManagement event = calendarEventRepository.findById(eventId).orElse(null);
		if (event != null) {
			event.setTitle(eventDetails.getTitle());
			event.setDescription(eventDetails.getDescription());
			event.setEventDate(eventDetails.getEventDate());
			event.setEventTime(eventDetails.getEventTime());
			event.setUpdatedAt(LocalDateTime.now());
			return calendarEventRepository.save(event);
		}
		return null;
	}
	
	// Delete event
	public void deleteEvent(Long eventId) {
		calendarEventRepository.deleteById(eventId);
	}
	
	// Add member to event
	public CalendarEventManagement addMemberToEvent(Long eventId, Long userId) {
		CalendarEventManagement event = calendarEventRepository.findById(eventId).orElse(null);
		NestUser user = nestUserRepository.findById(userId).orElse(null);
		
		if (event != null && user != null) {
			event.addMember(user);
			event.setUpdatedAt(LocalDateTime.now());
			return calendarEventRepository.save(event);
		}
		return null;
	}
	
	// Remove member from event
	public CalendarEventManagement removeMemberFromEvent(Long eventId, Long userId) {
		CalendarEventManagement event = calendarEventRepository.findById(eventId).orElse(null);
		NestUser user = nestUserRepository.findById(userId).orElse(null);
		
		if (event != null && user != null) {
			event.removeMember(user);
			event.setUpdatedAt(LocalDateTime.now());
			return calendarEventRepository.save(event);
		}
		return null;
	}
	
	// Get events created by user
	public List<CalendarEventManagement> getEventsByCreator(Long userId) {
		NestUser user = nestUserRepository.findById(userId).orElse(null);
		if (user != null) {
			return calendarEventRepository.findByCreator(user);
		}
		return List.of();
	}
	
	// Get events where user is a member
	public List<CalendarEventManagement> getEventsByMember(Long userId) {
		NestUser user = nestUserRepository.findById(userId).orElse(null);
		if (user != null) {
			return calendarEventRepository.findEventsByMember(user);
		}
		return List.of();
	}
	
	// Get all events for a user (created or as member)
	public List<CalendarEventManagement> getUserEvents(Long userId) {
		NestUser user = nestUserRepository.findById(userId).orElse(null);
		if (user != null) {
			return calendarEventRepository.findEventsByCreatorOrMember(user, user);
		}
		return List.of();
	}
	
	// Add multiple members to event
	public CalendarEventManagement addMembersToEvent(Long eventId, Set<Long> userIds) {
		CalendarEventManagement event = calendarEventRepository.findById(eventId).orElse(null);
		if (event != null) {
			for (Long userId : userIds) {
				NestUser user = nestUserRepository.findById(userId).orElse(null);
				if (user != null) {
					event.addMember(user);
				}
			}
			event.setUpdatedAt(LocalDateTime.now());
			return calendarEventRepository.save(event);
		}
		return null;
	}
}
