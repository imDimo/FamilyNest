package edu.uwgb.se372.familynest.event;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import edu.uwgb.se372.familynest.user.NestUser;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="CALENDAR_EVENT")
public class CalendarEventManagement {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="title", nullable=false)
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="event_date", nullable=false)
	private LocalDateTime eventDate;
	
	@Column(name="event_time")
	private String eventTime;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="creator_id", nullable=false)
	private NestUser creator;
	
	@ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
		name="CALENDAR_EVENT_MEMBERS",
		joinColumns=@JoinColumn(name="event_id"),
		inverseJoinColumns=@JoinColumn(name="user_id")
	)
	private Set<NestUser> members = new HashSet<>();
	
	@Column(name="created_at", nullable=false, updatable=false)
	private LocalDateTime createdAt = LocalDateTime.now();
	
	@Column(name="updated_at")
	private LocalDateTime updatedAt = LocalDateTime.now();
	
	public CalendarEventManagement() {}
	
	public CalendarEventManagement(String title, String description, LocalDateTime eventDate, NestUser creator) {
		this.title = title;
		this.description = description;
		this.eventDate = eventDate;
		this.creator = creator;
	}
	
	// Getters and Setters
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
	
	public LocalDateTime getEventDate() {
		return eventDate;
	}
	
	public void setEventDate(LocalDateTime eventDate) {
		this.eventDate = eventDate;
	}
	
	public String getEventTime() {
		return eventTime;
	}
	
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	
	public NestUser getCreator() {
		return creator;
	}
	
	public void setCreator(NestUser creator) {
		this.creator = creator;
	}
	
	public Set<NestUser> getMembers() {
		return members;
	}
	
	public void setMembers(Set<NestUser> members) {
		this.members = members;
	}
	
	public void addMember(NestUser member) {
		this.members.add(member);
	}
	
	public void removeMember(NestUser member) {
		this.members.remove(member);
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}
