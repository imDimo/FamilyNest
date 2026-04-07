package edu.uwgb.se372.familynest.event;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NestEventService {

	@Autowired
	private NestEventRepository eventRepository;
	
	public List<NestEvent> getAllEvents() {
		return eventRepository.findAll();
	}
	
	public NestEvent getEventById(Long eventId) {
		return eventRepository.findById(eventId).orElse(null);
	}
	
	public NestEvent createEvent(NestEvent event) {
		return eventRepository.save(event);
	}
	
	public NestEvent updateEvent(Long eventId, NestEvent event) {
		NestEvent existingUser = eventRepository.findById(eventId).orElse(null);
		if (existingUser != null) {
			existingUser.setTitle(event.getTitle());
			existingUser.setDescription(event.getDescription());
			return eventRepository.save(existingUser);
		}
		else {
			return null;
		}
	}
	
	public void deleteEventById(Long eventId) {
		eventRepository.deleteById(eventId);
	}
}
