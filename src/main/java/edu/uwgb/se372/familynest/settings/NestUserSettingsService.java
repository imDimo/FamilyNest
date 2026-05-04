package edu.uwgb.se372.familynest.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NestUserSettingsService {
	
	@Autowired
	private NestUserSettingsRepository nestUserSettingsRepository;
	
	public NestUserSettings createSettings() {
		NestUserSettings settings = new NestUserSettings();
		return nestUserSettingsRepository.save(settings);
	}
	
	public NestUserSettings getSettings(long settingsId) {
		return nestUserSettingsRepository.findById(settingsId).orElse(null);
	}

	public NestUserSettings updateSettings(long settingsId, NestUserSettings updatedSettings) {
    NestUserSettings settings = nestUserSettingsRepository.findById(settingsId).orElse(null);
    
    if (settings == null) return null;
    
    settings.setDarkMode(updatedSettings.getDarkMode());
    settings.setAllowAnnouncements(updatedSettings.getAllowAnnouncements());
    settings.setShowOnlineStatus(updatedSettings.getShowOnlineStatus());
    
    return nestUserSettingsRepository.save(settings);
}
	
	public Iterable<NestUserSettings> getAllSettings() {
		return nestUserSettingsRepository.findAll();
	}
	
	public void deleteSettingsById(long settingsId) {
		nestUserSettingsRepository.deleteById(settingsId);
	}
}
