package com.oracle.survey_respondant_service.service;

import java.util.List;
import java.util.Optional;

import com.oracle.survey_respondant_service.dto.ResponseDto;
import com.oracle.survey_respondant_service.entity.Response;
import com.oracle.survey_respondant_service.exception.RespondantServiceException;

public interface RespondantService {
	
	public void saveResponse(List<ResponseDto> response, Optional<Long> id, Long surveyId, Long versionId) throws RespondantServiceException;
	
	public List<ResponseDto> fetchResponse(Long versionId);
}
