package edu.uwgb.se372.familynest.session;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NestSessionRepository extends CrudRepository<NestSession, String> {
}
