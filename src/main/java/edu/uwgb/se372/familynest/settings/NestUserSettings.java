package edu.uwgb.se372.familynest.settings;

import java.io.Serializable;

import edu.uwgb.se372.familynest.user.NestUser;
import jakarta.persistence.*;

@Entity
@Table(name="settings")
public class NestUserSettings implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "settings_id")
	private Long id;
	
	@OneToOne
	@PrimaryKeyJoinColumn(name = "settings_id")
	NestUser user;
	
	private boolean darkMode;
	
	public NestUserSettings() {};
	public NestUserSettings(boolean darkMode) {
		this.darkMode = darkMode;
	}
	
	public long getId() {
		return id;
	}
	
	public boolean getDarkMode() {
		return darkMode;
	}
}
