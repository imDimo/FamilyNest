package edu.uwgb.se372.familynest.authority;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NestRoleService {
	
	@Autowired
	NestRoleRepository roleRepository;
	
	public NestRole create(String name, Collection<NestPrivilege> privileges) {
		NestRole role = roleRepository.findByName(name);
		if (name == null) {
			role = new NestRole(name);
			role.setPrivileges(privileges);
			roleRepository.save(role);
		}
		
		return role;
	}
	
	public NestRole findByName(String name) {
		return roleRepository.findByName(name);
	}
}
