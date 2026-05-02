package edu.uwgb.se372.familynest.settings;

import java.io.Serializable;

import edu.uwgb.se372.familynest.user.NestUser;
import jakarta.persistence.*;

@Entity
@Table(name="settings")
public class NestUserSettings implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "settings_id")
	private Long id;
	
	@OneToOne
	@PrimaryKeyJoinColumn(name = "settings_id")
	NestUser user;
	
	private boolean darkMode;
	private boolean allowAnnouncements;
	private boolean showOnlineStatus;
	
	public NestUserSettings() {};
	public NestUserSettings(boolean darkMode) {
		this.darkMode = darkMode;
	}
	
	public long getId() {
		return id;
	}
	
	public boolean getDarkMode() {
		return darkMode;
	}
	
	public void setDarkMode(boolean darkMode) {
		this.darkMode = darkMode;
	}
	public boolean getAllowAnnouncements() {
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
