package edu.uwgb.group2.familynest.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uwgb.group2.familynest.model.NestSession;

@Repository
public interface NestSessionRepository extends CrudRepository<NestSession, String> {
}
