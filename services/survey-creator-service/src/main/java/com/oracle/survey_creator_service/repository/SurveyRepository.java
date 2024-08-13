package com.oracle.survey_creator_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oracle.survey_creator_service.entity.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
	
}
