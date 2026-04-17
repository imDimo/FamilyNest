package edu.uwgb.se372.familynest.authority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class NestRoleDto {
	private Long id;
	private String name;
	
	public NestRoleDto() {}
	public NestRoleDto(NestRole role) {
		this.id = role.getId();
		this.name = role.getName();
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
