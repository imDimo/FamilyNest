package edu.uwgb.group2.familynest.websocket;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
	
	@Autowired
	WebSocketService webSocketService;
	
	@MessageMapping("/announcement")
	@SendTo("/topic/announcements")
	public TimedAnnouncement announcement(Announcement announcement) {
		String time = new SimpleDateFormat("hh:mm a").format(new Date());
		return new TimedAnnouncement(announcement.getTitle(), announcement.getContent(), time);
	}
}
