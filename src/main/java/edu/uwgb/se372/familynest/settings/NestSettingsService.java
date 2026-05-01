package edu.uwgb.se372.familynest.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NestSettingsService {
	
	@Autowired
	private NestUserSettingsRepository nestUserSettingsRepository;
	
	public NestUserSettings createSettings(long userId) {
		NestUserSettings settings = new NestUserSettings();
		settings.setUserId(userId);
		return nestUserSettingsRepository.save(settings);
	}
	
	public NestUserSettings getSettings(String sessionId) {
		return nestUserSettingsRepository.findById(sessionId).orElse(null);
	}
	
	public Iterable<NestUserSettings> getAllSettings() {
		return nestUserSettingsRepository.findAll();
	}
	
	public void deleteSettings(String sessionId) {
		nestUserSettingsRepository.deleteById(sessionId);
	}
}
