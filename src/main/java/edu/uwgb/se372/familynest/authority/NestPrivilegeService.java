package edu.uwgb.se372.familynest.authority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NestPrivilegeService {

	@Autowired
	NestPrivilegeRepository privilegeRepository;
	
	public NestPrivilege create(String name) {
		NestPrivilege privilege = privilegeRepository.findByName(name);
		if (name == null) {
			privilege = new NestPrivilege(name);
			privilegeRepository.save(privilege);
		}
		
		return privilege;
	}
	
	public NestPrivilege findByName(String name) {
		return privilegeRepository.findByName(name);
	}
}
