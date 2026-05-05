package edu.uwgb.se372.familynest.event;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uwgb.se372.familynest.user.NestUser;
import edu.uwgb.se372.familynest.user.NestUserRepository;

@Service
public class NestEventService {
	
	@Autowired
	private NestEventRepository calendarEventRepository;
	
	@Autowired
	private NestUserRepository nestUserRepository;
	
	// Create event
	public NestEvent create(NestEvent event) {
		event.setCreatedAt(LocalDateTime.now());
		event.setUpdatedAt(LocalDateTime.now());
		return calendarEventRepository.save(event);
	}
	
	// Get all events
	public List<NestEvent> getAllEvents() {
		return calendarEventRepository.findAll();
	}
	
	/**
	 * Returns all events whose {@code eventDate} falls within the given month/year.
	 *
	 * @param month 1-12
	 * @param year full year (e.g. 2026)
	 */
	public List<NestEvent> getEventsByMonthYear(int month, int year) {
		YearMonth ym = YearMonth.of(year, month);
		LocalDateTime startInclusive = ym.atDay(1).atStartOfDay();
		LocalDateTime endExclusive = ym.plusMonths(1).atDay(1).atStartOfDay();
		return calendarEventRepository.findEventsByEventDateRange(startInclusive, endExclusive);
	}
	
	// Get event by ID
	public NestEvent getEventById(Long eventId) {
		return calendarEventRepository.findById(eventId).orElse(null);
	}
	
	// Update event
	public NestEvent updateEvent(Long eventId, NestEventDto eventData) {
		NestEvent existingEvent = calendarEventRepository.findById(eventId).orElse(null);
		
		if (existingEvent == null) {
			System.err.println("Error retrieving user from database");
			return null;
		}
		
		if (!eventData.getTitle().isBlank()) {
			existingEvent.setTitle(eventData.getTitle());
		}
		
		existingEvent.setDescription(eventData.getDescription());
		
		if (!eventData.getEventDate().isBlank()) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
				existingEvent.setEventDate(LocalDateTime.parse(eventData.getEventDate(), formatter));
			} catch (Exception e) {
				// Keep existing date if parsing fails
			}
		}
		
		existingEvent.setEventTime(eventData.getEventTime());
		existingEvent.setUpdatedAt(LocalDateTime.now());
		
		return null;
	}
	
	// Delete event
	public void deleteEvent(Long eventId) {
		calendarEventRepository.deleteById(eventId);
	}
	
	// Add member to event
	public NestEvent addMemberToEvent(Long eventId, Long userId) {
		NestEvent event = calendarEventRepository.findById(eventId).orElse(null);
		NestUser user = nestUserRepository.findById(userId).orElse(null);
		
		if (event != null && user != null) {
			event.addMember(user);
			event.setUpdatedAt(LocalDateTime.now());
			return calendarEventRepository.save(event);
		}
		return null;
	}
	
	// Remove member from event
	public NestEvent removeMemberFromEvent(Long eventId, Long userId) {
		NestEvent event = calendarEventRepository.findById(eventId).orElse(null);
		NestUser user = nestUserRepository.findById(userId).orElse(null);
		
		if (event != null && user != null) {
			event.removeMember(user);
			event.setUpdatedAt(LocalDateTime.now());
			return calendarEventRepository.save(event);
		}
		return null;
	}
	
	// Get events created by user
	public List<NestEvent> getEventsByCreator(Long userId) {
		NestUser user = nestUserRepository.findById(userId).orElse(null);
		if (user != null) {
			return calendarEventRepository.findByCreator(user);
		}
		return List.of();
	}
	
	// Get events where user is a member
//	public List<NestEvent> getEventsByMember(Long userId) {
//		NestUser user = nestUserRepository.findById(userId).orElse(null);
//		if (user != null) {
//			return calendarEventRepository.findEventsByMember(user);
//		}
//		return List.of();
//	}
	
	// Get all events for a user (created or as member)
//	public List<NestEvent> getUserEvents(Long userId) {
//		NestUser user = nestUserRepository.findById(userId).orElse(null);
//		if (user != null) {
//			return calendarEventRepository.findEventsByCreatorOrMember(user, user);
//		}
//		return List.of();
//	}
	
	// Add multiple members to event
	public NestEvent addMembersToEvent(Long eventId, Set<Long> userIds) {
		NestEvent event = calendarEventRepository.findById(eventId).orElse(null);
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
