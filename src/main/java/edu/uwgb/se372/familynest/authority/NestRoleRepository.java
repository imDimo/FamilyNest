package edu.uwgb.se372.familynest.authority;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NestRoleRepository extends JpaRepository<NestRole, Long> {
	public NestRole findByName(String roleName);
}
