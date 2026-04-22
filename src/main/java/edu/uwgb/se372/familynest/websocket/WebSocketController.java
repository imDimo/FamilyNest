package edu.uwgb.se372.familynest.websocket;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.uwgb.se372.familynest.announcements.Announcement;
import edu.uwgb.se372.familynest.announcements.TimedAnnouncement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
	
	@Autowired
	private WebSocketService webSocketService;
	
	@MessageMapping("/announcement")
	@SendTo("/topic/announcements")
	public TimedAnnouncement announcement(Announcement announcement) {
		String time = new SimpleDateFormat("hh:mm a").format(new Date());
		return new TimedAnnouncement(announcement.getTitle(), announcement.getContent(), time);
	}
}
