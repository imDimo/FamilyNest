package edu.uwgb.se372.familynest.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.uwgb.se372.familynest.user.NestUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class NestEventDto {
	private Long id;
	private String title;
	private String description;
	private String eventDate;
	private String eventTime;
	private List<Long> memberIds;
	private String createdAt;
	private String updatedAt;
	
	public NestEventDto() {}
	public NestEventDto(NestEvent event) {
		this.id = event.getId();
		this.title = event.getTitle();
		this.description = event.getDescription();
		this.eventDate = event.getEventDate().toString();
		this.eventTime = event.getEventTime().toString();
		this.memberIds = new ArrayList<>();
		memberIds.addAll(event.getMembers().stream().map((m) -> m.getId()).toList());
		this.createdAt = event.getCreatedAt().toString();
		this.updatedAt = event.getUpdatedAt().toString();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	public List<Long> getMemberIds() {
		return memberIds;
	}
	public void setMemberIds(List<Long> memberIds) {
		this.memberIds = memberIds;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
}
