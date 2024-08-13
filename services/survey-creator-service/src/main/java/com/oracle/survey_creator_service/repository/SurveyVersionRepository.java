package com.oracle.survey_creator_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.oracle.survey_creator_service.entity.SurveyVersion;


public interface SurveyVersionRepository extends JpaRepository<SurveyVersion, Long> {
    
    @Query(value = "SELECT survey_id FROM survey_version WHERE survey_version_id = :version", nativeQuery = true)
    Long findSurveyIdByVersion(@Param("version") Long version);
}
