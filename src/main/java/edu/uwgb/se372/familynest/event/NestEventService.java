package edu.uwgb.se372.familynest.event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uwgb.se372.familynest.user.NestUser;
import edu.uwgb.se372.familynest.user.NestUserService;

@Service
public class NestEventService {
	
	@Autowired
	private NestEventRepository nestEventRepository;
	
	@Autowired
	private NestUserService userService;
	
	// Create event
	public NestEvent create(NestEvent event) {
		event.setCreatedAt(LocalDate.now());
		event.setUpdatedAt(LocalDate.now());
		
		return nestEventRepository.save(event);
	}
	
	// Get all events
	public List<NestEvent> getAllEvents() {
		return nestEventRepository.findAll();
	}
	
	/**
	 * Returns all events whose {@code eventDate} falls within the given month/year.
	 *
	 * @param month 1-12
	 * @param year full year (e.g. 2026)
	 */
	public List<NestEvent> getEventsByMonthYear(int month, int year) {
		YearMonth ym = YearMonth.of(year, month);
		LocalDate startInclusive = ym.atDay(1);
		LocalDate endExclusive = ym.plusMonths(1).atDay(1);
		return nestEventRepository.findEventsByEventDateRange(startInclusive, endExclusive);
	}
	
	// Get event by ID
	public NestEvent getEventById(Long eventId) {
		return nestEventRepository.findById(eventId).orElse(null);
	}
	
	// Update event
	public NestEvent updateEvent(Long eventId, NestEventDto eventData) {
		NestEvent existingEvent = nestEventRepository.findById(eventId).orElse(null);
		
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
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LL-dd-y");
				existingEvent.setEventDate(LocalDate.parse(eventData.getEventDate(), formatter));
			} catch (Exception e) {
				// Keep existing date if parsing fails
			}
		}
		
		existingEvent.setMembers(new HashSet<NestUser>(eventData.getMemberIds()
				.stream().map((id) -> {
					NestUser u = null;
					try {
						u = userService.loadUserById(id);
					}
					catch (Exception e) {
						System.err.println(e.getMessage());
					}
					return u;
				}
			).toList()
		));
		
		existingEvent.setEventTime(eventData.getEventTime());
		existingEvent.setUpdatedAt(LocalDate.now());
		
		return null;
	}
	
	// Delete event
	public void deleteEvent(Long eventId) {
		nestEventRepository.deleteById(eventId);
	}
	
	// Add member to event
	public NestEvent addMemberToEvent(Long eventId, Long userId) {
		NestEvent event = nestEventRepository.findById(eventId).orElse(null);
		if (event == null) {
			return null;
		}
		
		NestUser user = null;
		try {
			user = userService.loadUserById(userId);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		
		event.addMember(user);
		event.setUpdatedAt(LocalDate.now());
		return nestEventRepository.save(event);
	}
	
	// Remove member from event
	public NestEvent removeMemberFromEvent(Long eventId, Long userId) {
		NestEvent event = nestEventRepository.findById(eventId).orElse(null);
		if (event == null) {
			return null;
		}
		
		NestUser user = null;
		try {
			user = userService.loadUserById(userId);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
		
		event.removeMember(user);
		event.setUpdatedAt(LocalDate.now());
		return nestEventRepository.save(event);
	}
	
	// Get events created by user
	public List<NestEvent> getEventsByCreator(Long userId) {
		NestUser user = null;
		try {
			user = userService.loadUserById(userId);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			return List.of();
		}
		
		return nestEventRepository.findByCreator(user);
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
		NestEvent event = nestEventRepository.findById(eventId).orElse(null);
		if (event != null) {
			for (Long userId : userIds) {
				try {
					NestUser user = userService.loadUserById(userId);
					event.addMember(user);
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			
			event.setUpdatedAt(LocalDate.now());
			return nestEventRepository.save(event);
		}
		return null;
	}
}
