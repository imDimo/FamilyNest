package edu.uwgb.se372.familynest.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NestUserRepository extends JpaRepository<NestUser, Long> {}
