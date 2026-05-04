package edu.uwgb.se372.familynest.announcements;

public class Announcement {
	private String title;
	private String content;
	private String time;
	private String senderId;
	
	public Announcement() {}
	
	public Announcement(String title, String content, String time, String senderId) {
		this.title = title;
		this.content = content;
		this.time = time;
		this.senderId = senderId;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
