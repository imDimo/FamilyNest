package edu.uwgb.se372.familynest.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NestSessionService {
	
	@Autowired
	private NestSessionRepository nestSessionRepository;
	
	public NestSession createSession(long userId) {
		NestSession session = new NestSession();
		session.setUserId(userId);
		return nestSessionRepository.save(session);
	}
	
	public NestSession getSession(String sessionId) {
		return nestSessionRepository.findById(sessionId).orElse(null);
	}
	
	public Iterable<NestSession> getAllSessions() {
		return nestSessionRepository.findAll();
	}
	
	public void deleteSession(String sessionId) {
		nestSessionRepository.deleteById(sessionId);
	}
}
