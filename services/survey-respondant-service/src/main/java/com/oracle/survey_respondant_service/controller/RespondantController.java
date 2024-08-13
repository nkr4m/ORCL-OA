package com.oracle.survey_respondant_service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oracle.survey_respondant_service.dto.ResponseDto;
import com.oracle.survey_respondant_service.entity.Response;
import com.oracle.survey_respondant_service.exception.RespondantServiceException;
import com.oracle.survey_respondant_service.service.RespondantService;
import com.oracle.survey_respondant_service.service.ResponseServiceImpl;

@RestController
@CrossOrigin
public class RespondantController {
	
	@Autowired
	RespondantService responseService;
	
	@PostMapping("save-response")
	public void saveResponse(@RequestBody List<ResponseDto> responseLi, 
			@RequestParam Optional<Long> userId,
			@RequestParam Long surveyId, @RequestParam Long versionId) throws RespondantServiceException {
		responseService.saveResponse(responseLi, userId, surveyId, versionId);
	}
	
	@GetMapping("fetch-results")
	public ResponseEntity<List<ResponseDto>> getMethodName(@RequestParam Long versionId) {
		return new ResponseEntity<List<ResponseDto>>(responseService.fetchResponse(versionId), HttpStatus.OK);
	}
	
	
}
