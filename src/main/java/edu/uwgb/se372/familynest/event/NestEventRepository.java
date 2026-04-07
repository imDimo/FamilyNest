package edu.uwgb.se372.familynest.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NestEventRepository extends JpaRepository<NestEvent, Long> {}
