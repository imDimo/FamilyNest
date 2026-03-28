package edu.uwgb.group2.familynest.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.uwgb.group2.familynest.model.NestUser;

@Repository
public interface NestUserRepository extends JpaRepository<NestUser, Long> {}
