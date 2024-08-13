package com.oracle.survey_respondant_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.survey_respondant_service.dto.ResponseDto;
import com.oracle.survey_respondant_service.entity.Response;
import com.oracle.survey_respondant_service.exception.RespondantServiceException;
import com.oracle.survey_respondant_service.repository.ResponseRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ResponseServiceImpl implements RespondantService {
	
	private static final Logger LOGGER = Logger.getLogger(ResponseServiceImpl.class.getName());

	
	@Autowired
	ResponseRepository responseRepository;
	
	public void saveResponse(List<ResponseDto> response, Optional<Long> id, Long surveyId, Long versionId) throws RespondantServiceException {
		String userId = "";
		if(id.isEmpty()) {
			userId = uUIDString();
		}else {
			userId = id.get().toString();
		}
		
		if(surveyId == null || versionId == null) {
			throw new RespondantServiceException("Either survey id or version id is not defined");
		}
		
		for(ResponseDto resDto : response) {
				Response res = new Response();
				res.setUserId(userId);
				res.setSurveyId(surveyId);
				res.setVersionId(versionId);
				res.setResponse(resDto.getResponse());
				res.setSectionId(resDto.getSectionId());
				
				responseRepository.saveAndFlush(res);
		}
		
		LOGGER.info(String.format("Response was added for the surveyId : " + 
		surveyId + ", versionId : " + versionId));
		
		
	}
	
	public String uUIDString() {
		return UUID.randomUUID().toString();
	}

	public List<ResponseDto> fetchResponse(Long versionId) {
		
		List<ResponseDto> res = new ArrayList<>();
		
		List<Response> list = responseRepository.findByVersionId(versionId);
		
		for(Response r : list) {
			ResponseDto dto = new ResponseDto();
			dto.setResponse(r.getResponse());
			dto.setResponseId(r.getResponseId());
			dto.setSectionId(r.getSectionId());
			dto.setSurveyId(r.getSurveyId());
			dto.setUserId(r.getUserId());
			dto.setVersionId(r.getVersionId());
			
			res.add(dto);
		}
		
		return res;
	}

}
