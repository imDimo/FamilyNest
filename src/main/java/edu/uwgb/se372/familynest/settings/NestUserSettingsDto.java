package edu.uwgb.se372.familynest.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class NestUserSettingsDto {
    private Long id;
    private Boolean darkMode;
    private Boolean allowAnnouncements;
    private Boolean showOnlineStatus;
    
    public NestUserSettingsDto() {}
    public NestUserSettingsDto(NestUserSettings settings) {
        this.id = settings.getId();
        this.darkMode = settings.getDarkMode();
        this.allowAnnouncements = settings.getAllowAnnouncements();
        this.showOnlineStatus = settings.getShowOnlineStatus();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Boolean getDarkMode() { return darkMode; }
    public void setDarkMode(Boolean darkMode) { this.darkMode = darkMode; }
    
    public Boolean isAllowAnnouncements() { return allowAnnouncements; }
    public void setAllowAnnouncements(Boolean allowAnnouncements) { this.allowAnnouncements = allowAnnouncements; }
    
    public Boolean getShowOnlineStatus() { return showOnlineStatus; }
    public void setShowOnlineStatus(Boolean showOnlineStatus) { this.showOnlineStatus = showOnlineStatus; }
}