package edu.uwgb.group2.familynest.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	public <T> void sendMessageToUser(String username, T message) {
		messagingTemplate.convertAndSendToUser(username, "/topic/announcements", message);
	}
	
	public <T> void sendMessage(T message) {
		messagingTemplate.convertAndSend(message);
	}
}
