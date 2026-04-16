package edu.uwgb.se372.familynest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import edu.uwgb.se372.familynest.authority.*;
import edu.uwgb.se372.familynest.security.NestPasswordEncoder;
import edu.uwgb.se372.familynest.user.NestUser;
import edu.uwgb.se372.familynest.user.NestUserRepository;
import edu.uwgb.se372.familynest.user.NestUserService;

@Component
public class NestDataSetup implements ApplicationListener<ContextRefreshedEvent> {
	
	@Autowired
	private NestUserService userService;
	
	@Autowired
	private NestRoleService roleService;
	
	@Autowired
	private NestPrivilegeService privilegeService;
	
	@Autowired
	private NestPasswordEncoder passwordEncoder;
	
	// Initialize database
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		NestPrivilege viewCalendarPrivilege = privilegeService.create("VIEW_CALENDAR");
		NestPrivilege editCalendarPrivilege = privilegeService.create("EDIT_CALENDAR");
		NestPrivilege viewGalleryPrivilege = privilegeService.create("VIEW_GALLERY");
		NestPrivilege editGalleryPrivilege = privilegeService.create("EDIT_GALLERY");
		NestPrivilege manageUserPrivilege = privilegeService.create("MANAGE_USER");
		NestPrivilege manageNestPrivilege = privilegeService.create("MANAGE_NEST");
		
		List<NestPrivilege> userPrivileges = Arrays.asList(
				viewCalendarPrivilege,
				editCalendarPrivilege,
				viewGalleryPrivilege,
				editGalleryPrivilege
			);
		
		NestRole roleUser = roleService.create("ROLE_USER", userPrivileges);
		
		List<NestPrivilege> adminPrivileges = Arrays.asList(
				viewCalendarPrivilege, 
				editCalendarPrivilege, 
				viewGalleryPrivilege, 
				editGalleryPrivilege,
				manageUserPrivilege,
				manageNestPrivilege
			);
		
		NestRole roleAdmin = roleService.create("ROLE_ADMIN", adminPrivileges);
	
		userService.create("admin", "password", Arrays.asList(roleAdmin, roleUser));
	}
}
