package edu.uwgb.se372.familynest.user;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.uwgb.se372.familynest.authority.NestRole;

@Repository
public interface NestUserRepository extends JpaRepository<NestUser, Long> {
	public NestUser findByUsername(String username);
	public List<NestUser> findByRolesIn(Collection<NestRole> roles);
}
