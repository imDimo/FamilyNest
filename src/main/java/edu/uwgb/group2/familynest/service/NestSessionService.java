package edu.uwgb.group2.familynest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uwgb.group2.familynest.db.NestSessionRepository;
import edu.uwgb.group2.familynest.model.NestSession;

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
