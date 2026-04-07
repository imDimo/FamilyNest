package edu.uwgb.se372.familynest.websocket;

public class Announcement {
	private String title;
	private String content;
	
	public Announcement() {}
	
	public Announcement(String title, String content) {
		this.title = title;
		this.content = content;
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
}
