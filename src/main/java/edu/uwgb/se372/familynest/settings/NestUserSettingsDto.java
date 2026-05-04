package edu.uwgb.se372.familynest.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class NestUserSettingsDto {
	private Long id;
	private boolean darkMode;
	private boolean allowAnnouncements;
	private boolean showOnlineStatus;
	
	public NestUserSettingsDto() {}
	public NestUserSettingsDto(NestUserSettings settings) {
		this.id = settings.getId();
		this.darkMode = settings.getDarkMode();
		this.allowAnnouncements = settings.getAllowAnnouncements();
		this.showOnlineStatus = settings.getShowOnlineStatus();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean getDarkMode() {
		return darkMode;
	}
	
	public void setDarkMode(boolean darkMode) {
		this.darkMode = darkMode;
	}
	public boolean isAllowAnnouncements() {
		return allowAnnouncements;
	}
	public void setAllowAnnouncements(boolean allowAnnouncements) {
		this.allowAnnouncements = allowAnnouncements;
	}
	public boolean getShowOnlineStatus() {
		return showOnlineStatus;
	}
	public void setShowOnlineStatus(boolean showOnlineStatus) {
		this.showOnlineStatus = showOnlineStatus;
	}
}
