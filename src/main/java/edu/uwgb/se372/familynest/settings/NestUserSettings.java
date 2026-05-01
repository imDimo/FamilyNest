package edu.uwgb.se372.familynest.settings;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("sessions")
public class NestUserSettings {
	
	@Id
	private String settingsId;
	
	private Long userId;
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId ) {
		this.userId = userId;
	}
}
