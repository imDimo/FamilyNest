package edu.uwgb.se372.familynest.authority;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NestPrivilegeRepository extends JpaRepository<NestPrivilege, Long> {
	public NestPrivilege findByName(String privilegeName);
}
