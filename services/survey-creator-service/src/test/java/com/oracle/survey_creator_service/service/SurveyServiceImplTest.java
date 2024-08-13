package com.oracle.survey_creator_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.oracle.survey_creator_service.dto.SurveyDto;
import com.oracle.survey_creator_service.dto.SurveySectionDto;
import com.oracle.survey_creator_service.dto.SurveySectionOptionDto;
import com.oracle.survey_creator_service.entity.Section;
import com.oracle.survey_creator_service.entity.SectionOption;
import com.oracle.survey_creator_service.entity.Survey;
import com.oracle.survey_creator_service.entity.SurveyVersion;
import com.oracle.survey_creator_service.exception.CreatorServiceException;
import com.oracle.survey_creator_service.repository.SectionOptionRepository;
import com.oracle.survey_creator_service.repository.SectionRepository;
import com.oracle.survey_creator_service.repository.SurveyRepository;
import com.oracle.survey_creator_service.repository.SurveyVersionRepository;

import antlr.Version;

import static org.mockito.Mockito.doThrow;
import org.springframework.dao.DataAccessException;
import jakarta.persistence.EntityNotFoundException;

public class SurveyServiceImplTest {

    @Mock
    private SurveyRepository surveyRepo;

    @Mock
    private SurveyVersionRepository surveyVersionRepo;

    @Mock
    private SectionRepository sectionRepo;

    @Mock
    private SectionOptionRepository sectionOptionRepo;

    @InjectMocks
    private SurveyServiceImpl surveyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void createSurvey_SUCCESS() {
    	Survey survey = new Survey();
    	survey.setTitle("NEW SURVEY");
    	survey.setDescription("RANDOM DESC");
    	
    	//mock surveyRepo saveAndFlush
    	when(surveyRepo.save(any(Survey.class))).thenReturn(survey);
    }
    
    @Test
    public void createSurvey_FAIL() {
        Survey survey = new Survey();
        survey.setTitle("NEW SURVEY");
        survey.setDescription("RANDOM DESC");

        // Mock surveyRepo saveAndFlush to throw an exception
        doThrow(new DataAccessException("DB error") {}).when(surveyRepo).saveAndFlush(any(Survey.class));

        // Verify that CreatorServiceException is thrown
        assertThrows(CreatorServiceException.class, () -> surveyService.createSurvey(survey), 
                "The system is unable to create a version for the survey at the moment");
    }
    
    @Test
    public void createSurveyVersion_SUCCESS() throws CreatorServiceException {
    	
    	SurveyVersion surveyVersion = new SurveyVersion();
    	Long surveyId = 1L;
    	
    	//existing survey present in the mock db
    	Survey existingSurvey = new Survey();
    	existingSurvey.setVersions(new ArrayList<>());
    	
    	//mock surveyRepo findById
    	when(surveyRepo.findById(1L)).thenReturn(Optional.of(existingSurvey));
    	
    	//mock surveyRepo save
    	when(surveyRepo.save(any(Survey.class))).thenReturn(existingSurvey);
    	
    	
    	surveyService.createSurveyVersion(surveyVersion, surveyId);
    	
    	verify(surveyRepo, times(1)).save(any(Survey.class));
    	
    }
    
    @Test
    public void createSurveyVersion_FAIL() throws CreatorServiceException {
    	
    	SurveyVersion surveyVersion = new SurveyVersion();
    	Long surveyId = 100L;
    	//mock surveyRepo findById
    	when(surveyRepo.findById(1L)).thenReturn(Optional.empty());
    	assertThrows(CreatorServiceException.class, () -> surveyService.createSurveyVersion(surveyVersion, surveyId));
    }
    
    @Test
    public void createSurveySection_SUCCESS() {
    	SurveyVersion version = new SurveyVersion();
    	version.setSurveyVersionId(1L);
    	version.setSections(new ArrayList<>());
    	
    	SurveySectionDto sectionDto = new SurveySectionDto();
    	sectionDto.setSectionTitle("RANDOM TITLE");
    	sectionDto.setSectionType("HEADING");
    	
    	Section section = new Section();
    	section.setSectionTitle("RANDOM TITLE");
    	section.setSectionType("HEADING");
    	section.setSectionId(100L);
    	
    	List<Section> sectionLi = new ArrayList<>();
    	sectionLi.add(section);
    	version.setSections(sectionLi);
    	

    	
    	//mock surveyVersionRepo findById
    	when(surveyVersionRepo.findById(1L)).thenReturn(Optional.of(version));
    	
    	//mock sectionRRepo save
    	when(sectionRepo.save(any(Section.class))).thenReturn(section);
    	
    	//mock sectionRepo findById
    	when(sectionRepo.findById(100L)).thenReturn(Optional.of(section));
    	
    	//mock surveyVersionRepo save
    	when(surveyVersionRepo.save(any(SurveyVersion.class))).thenReturn(version);
    }
    
    @Test
    public void createSurveySection_FAIL() {
    	SurveyVersion version = new SurveyVersion();
    	version.setSections(new ArrayList<>());
    	

    	
    	//mock surveyVersionRepo findById
    	assertThrows(CreatorServiceException.class, () -> surveyService.createSurveySection(new SurveySectionDto(),version.getSurveyVersionId()), 
                "The system is unable to create a version for the survey at the moment");
    	
    }
    
    
    
    
}
