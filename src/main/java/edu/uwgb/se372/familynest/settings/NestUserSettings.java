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
	private Long settingsId;
	
	@OneToOne
	@PrimaryKeyJoinColumn(name = "settings_id")
	NestUser user;
	
	private Boolean dark_mode;
	
	public NestUserSettings() {};
	public NestUserSettings(boolean dark_mode) {
		this.dark_mode = dark_mode;
	}
}
