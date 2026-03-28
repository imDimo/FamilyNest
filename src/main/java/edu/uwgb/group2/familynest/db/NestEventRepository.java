package edu.uwgb.group2.familynest.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.uwgb.group2.familynest.model.NestEvent;

@Repository
public interface NestEventRepository extends JpaRepository<NestEvent, Long> {}
