package com.oracle.survey_creator_service.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.oracle.survey_creator_service.dto.ResponseDto;
import com.oracle.survey_creator_service.dto.SurveyDto;
import com.oracle.survey_creator_service.dto.SurveySectionDto;
import com.oracle.survey_creator_service.dto.UserResult;
import com.oracle.survey_creator_service.entity.Section;
import com.oracle.survey_creator_service.entity.Survey;
import com.oracle.survey_creator_service.entity.SurveyVersion;
import com.oracle.survey_creator_service.exception.CreatorServiceException;
import com.oracle.survey_creator_service.service.SurveyService;

@RestController
@CrossOrigin
public class SurveyCreatorController {
	
	@Autowired
	SurveyService surveyService;
	
	@PostMapping("create-survey")
	public void createSurvey(@RequestBody Survey survey) throws CreatorServiceException {
		surveyService.createSurvey(survey);
	}
	
	@PostMapping("create-survey-version")
	public void createSurveyVersion(@RequestBody SurveyVersion surveyVersion, @RequestParam Long surveyId) throws CreatorServiceException {
		surveyService.createSurveyVersion(surveyVersion, surveyId);
	}
	
	@PostMapping("create-survey-section")
	public void createSurveySecion(@RequestBody SurveySectionDto sectionDto, @RequestParam Long surveyVersionId) throws CreatorServiceException {
		surveyService.createSurveySection(sectionDto, surveyVersionId);
	}

	@GetMapping("view-all-survey")
	public ResponseEntity<Page<Survey>> viewAllSurvey(@RequestParam Optional<Integer> pageNumber, @RequestParam Optional<Integer> pageSize) {
		return new ResponseEntity<Page<Survey>>(surveyService.viewAllSurvey(pageNumber, pageSize), HttpStatus.OK);
	}
	
	@GetMapping("view-survey")
	public ResponseEntity<Survey> viewSurvey(@RequestParam Long surveyId) {
		return new ResponseEntity<Survey>(surveyService.viewSurvey(surveyId), HttpStatus.OK);
	}
	
	@GetMapping("view-survey-sp-version")
	public ResponseEntity<SurveyDto> viewSurveyV(@RequestParam Long surveyId, @RequestParam Long versionId) {
		return new ResponseEntity<SurveyDto>(surveyService.viewSurvey(surveyId, versionId), HttpStatus.OK);
	}
	
	@GetMapping("view-survey-sp2-version")
	public ResponseEntity<SurveyDto> viewSurveyP(@RequestParam Long surveyId) {
		return new ResponseEntity<SurveyDto>(surveyService.viewSurveyDto(surveyId), HttpStatus.OK);
	}
	

	@PutMapping("change-version-status")
	public void changeStatus(@RequestParam Long versionId) {
		surveyService.changeStatus(versionId);
	}
	
	@GetMapping("view-version-section")
	public ResponseEntity<List<Section>> viewSurveySection(@RequestParam Long versionId){
		List<Section> res = surveyService.viewSurveySecion(versionId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PutMapping("modify-section-order")
	public void modifySectionOrder(@RequestBody List<Section> list, @RequestParam Long versionId) {
		surveyService.modifySectionOrder(list, versionId);
	}
	
	@GetMapping("modify-version-status")
	public void modifyVersionStatus(@RequestParam Long versionId) {
		surveyService.modifyVersionStatus(versionId);
	}
	
	@Autowired
    private RestTemplate restTemplate;

	
	
	@GetMapping("results/fetch-results")
	public ResponseEntity<List<UserResult>> fetchResults(@RequestParam Long versionId) {
		

		//get the list of users who have responded for the version
		String serviceRespondantUrl = "http://survey-respondant-service/fetch-results?versionId=" + versionId;
		ResponseEntity<List<ResponseDto>> responseEntity = restTemplate.exchange(
	            serviceRespondantUrl,
	            HttpMethod.GET,
	            null,
	            new ParameterizedTypeReference<List<ResponseDto>>() {}
	        );
		
		
		List<ResponseDto> responseList = responseEntity.getBody();
		
		HashMap<String, Integer> map = new HashMap<>();
		map.put("Anonymous", 0);
		List<String> tempAn = new ArrayList<>();
		for(ResponseDto r : responseList) {
			if(map.containsKey(r.getUserId())) {
				continue;
			}else {
				try {
					Long.parseLong(r.getUserId());
					map.put(r.getUserId(), 1);
				}catch(Exception e) {
					if(tempAn.contains(r.getUserId())) {
						continue;
					}else {
						tempAn.add(r.getUserId());
						map.put("Anonymous", map.get("Anonymous") + 1);
					}
				}
			}
		}
		
		List<UserResult> res = new ArrayList<>();
		
		for(String s : map.keySet()) {
			res.add(new UserResult(s, map.get(s)));
		}
		return new ResponseEntity<List<UserResult>>(res, HttpStatus.OK);
	}
	
	
	public boolean isLong(String str) {
	    if (str == null || str.isEmpty()) {
	        return false;
	    }
	    try {
	        Long.parseLong(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	
	

	
	

}
