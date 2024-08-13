package com.oracle.survey_creator_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.oracle.survey_creator_service.dto.SurveyDto;
import com.oracle.survey_creator_service.dto.SurveySectionDto;
import com.oracle.survey_creator_service.entity.Section;
import com.oracle.survey_creator_service.entity.Survey;
import com.oracle.survey_creator_service.entity.SurveyVersion;
import com.oracle.survey_creator_service.exception.CreatorServiceException;

public interface SurveyService {
	
	    //create
		public void createSurvey(Survey survey) throws CreatorServiceException;
		
		public void createSurveyVersion(SurveyVersion surveyVersion, Long surveyId) throws CreatorServiceException;
		
		public void createSurveySection(SurveySectionDto section, Long surveyVersionId) throws CreatorServiceException;
		
		
		
		//read
		public Page<Survey> viewAllSurvey(Optional<Integer> pageNumber, Optional<Integer> pageSize);
		
		public Survey viewSurvey(Long surveyId);
		
		public SurveyDto viewSurvey(Long surveyId, Long versionId);
		
		public List<Section> viewSurveySecion(Long surveyVersionId);
		


		public void changeStatus(Long versionId);

		public void modifySectionOrder(List<Section> list, Long versionId);

		public void modifyVersionStatus(Long versionId);

		public SurveyDto viewSurveyDto(Long surveyId);

	
}
