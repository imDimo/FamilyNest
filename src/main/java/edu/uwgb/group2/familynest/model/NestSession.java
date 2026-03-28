package edu.uwgb.group2.familynest.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("sessions")
public class NestSession {
	
	@Id
	private String sessionId;
	
	private Long userId;
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId ) {
		this.userId = userId;
	}
}
