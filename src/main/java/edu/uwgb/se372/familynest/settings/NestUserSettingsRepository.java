package edu.uwgb.se372.familynest.settings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NestUserSettingsRepository extends JpaRepository<NestUserSettings, String> {}
