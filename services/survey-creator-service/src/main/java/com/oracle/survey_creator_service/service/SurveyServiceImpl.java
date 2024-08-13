package com.oracle.survey_creator_service.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SurveyServiceImpl implements SurveyService {
	
	private static final Logger LOGGER = Logger.getLogger(SurveyServiceImpl.class.getName());

	
	@Autowired
	SurveyRepository surveyRepo;
	
	@Autowired
	SurveyVersionRepository surveyVersionRepo;
	
	@Autowired
	SectionRepository sectionRepo;
	
	@Autowired
	SectionOptionRepository sectionOptionRepo;

	@Override
	public void createSurvey(Survey survey) throws CreatorServiceException {
		survey.setCreatedOn(LocalDateTime.now());
		try {
			surveyRepo.saveAndFlush(survey);
		}catch (Exception e) {
			throw new CreatorServiceException("The system is unable to create a version for the survey at the moment");
		}
	}

	@Override
	public void createSurveyVersion(SurveyVersion surveyVersion, Long surveyId) throws CreatorServiceException {
	    Optional<Survey> optionalSurvey = surveyRepo.findById(surveyId);
	    if (optionalSurvey.isPresent()) {
	        // We have survey
	        surveyVersion.setCreatedOn(LocalDateTime.now());
	        surveyVersion.setLive(false);
	        Survey survey = optionalSurvey.get();
	        
	        // Set the survey in surveyVersion
	        surveyVersion.setSurvey(survey);
	        
	        List<SurveyVersion> versions = survey.getVersions();
	        versions.add(surveyVersion);
	        survey.setVersions(versions);
	        
	        LOGGER.info("Survey was ceated Successfully");
	        // Save the survey, which will cascade save the surveyVersion
	        surveyRepo.save(survey);
	    } else {
	    	LOGGER.info("System has encountered an exception while creating the survey");
	    	throw new CreatorServiceException("The system is unable to create a version for the survey at the moment");
	    }
	}


	@Override
	public void createSurveySection(SurveySectionDto sectionDto, Long surveyVersionId) throws CreatorServiceException {
	    // Retrieve the survey version by ID
	    Optional<SurveyVersion> optionalSurveyVersion = surveyVersionRepo.findById(surveyVersionId);
	    if(optionalSurveyVersion.isPresent()) {            
	        SurveyVersion version = optionalSurveyVersion.get();
	        
	        // Create a new section and set its properties
	        Section section = new Section();
	        section.setSectionTitle(sectionDto.sectionTitle);
	        section.setSectionType(sectionDto.sectionType);
	        section.setSurveyVersion(version);

	        Long newSectionId = sectionRepo.save(section).getSectionId();

	        // Add the new section to the survey version
	        List<Section> sections = version.getSections();
	        section.setOrderIndex((long)sections.size());
	        sections.add(section);
	        version.setSections(sections);
	        

	        
	        Section sectionTemp = sectionRepo.findById(newSectionId).get();
	        
	        List<SectionOption> li = sectionTemp.getSectionOptions();
	        if(li == null) {
	        	li = new ArrayList<>();
	        }
	        
		               
	        
	        // Add options if the section type is "CHECKBOX" or "RADIO"
	        if("CHECKBOX".equals(sectionDto.sectionType) || "RADIO".equals(sectionDto.sectionType)) {
	            for(SurveySectionOptionDto op : sectionDto.sectionOptions) {
	                SectionOption option = new SectionOption();
	                option.setOptionTitle(op.option);
	                option.setSection(sectionTemp);
	                sectionOptionRepo.save(option);
	            }
	        }
	        
	        surveyVersionRepo.save(version);
	        LOGGER.info("Version was created for the survey");
	        
	        
	        
	    } else {
	    	LOGGER.info("System has encountered an exception while creating the version");
	    	throw new CreatorServiceException("The system cannot find a version related to the section");
	    }
	}


	
	@Override
    public Page<Survey> viewAllSurvey(Optional<Integer> pageNumber, Optional<Integer> pageSize) {
        int currentPage = pageNumber.orElse(0);
        int currentSize = pageSize.orElse(10);

        Pageable pageable = PageRequest.of(currentPage, currentSize, Sort.by(Sort.Direction.DESC, "surveyId"));
        
        return surveyRepo.findAll(pageable);
    }



	
	@Override
	public Survey viewSurvey(Long surveyId) {
	    Optional<Survey> optionalSurvey = surveyRepo.findById(surveyId);
	    if (optionalSurvey.isPresent()) {
	        Survey survey = optionalSurvey.get();

	        // Sort sections in each SurveyVersion
	        for (SurveyVersion version : survey.getVersions()) {
	        	
	            // Get the sections list
	            List<Section> sections = version.getSections();
	            
	            // Sort the sections by orderIndex
	            sections.sort(Comparator.comparingLong(Section::getOrderIndex));
	        }

	        return survey;
	    } else {
	        return null;
	    }
	}
	
	@Override
	public SurveyDto viewSurvey(Long surveyId, Long versionId) {
		SurveyDto dto = new SurveyDto();
			
			Optional<Survey> optionalSurvey = surveyRepo.findById(surveyId);
		    if (optionalSurvey.isPresent()) {
		        Survey survey = optionalSurvey.get();
		        
		        dto.setActiveVersion(survey.getActiveVersion());
		        dto.setCreatedOn(survey.getCreatedOn());
		        dto.setDescription(survey.getDescription());
		        dto.setOwner(survey.getOwner());
		        dto.setTitle(survey.getTitle());
		        dto.setSurveyId(survey.getSurveyId() );

		        // Sort sections in each SurveyVersion
		        for (SurveyVersion version : survey.getVersions()) {
		        	
		        	if(version.getSurveyVersionId() == versionId) {
		        		List<SurveyVersion> temp = new ArrayList<>();
		        		temp.add(version);
		        		dto.setVersions(temp);
		        		break;
		        	}
		        	
		            // Get the sections list
		            List<Section> sections = version.getSections();
		            
		            // Sort the sections by orderIndex
		            sections.sort(Comparator.comparingLong(Section::getOrderIndex));
		        }

		        return dto;
		    } else {
		        return null;
		    }
		
		
	}
	
	@Override
	public SurveyDto viewSurveyDto(Long surveyId) {
		
		
		SurveyDto dto = new SurveyDto();
		
			
			//fetch the later version of survey
			Optional<Survey> optionalSurvey = surveyRepo.findById(surveyId);
		    if (optionalSurvey.isPresent()) {
		        Survey survey = optionalSurvey.get();
		        
		        dto.setActiveVersion(survey.getActiveVersion());
		        dto.setCreatedOn(survey.getCreatedOn());
		        dto.setDescription(survey.getDescription());
		        dto.setOwner(survey.getOwner());
		        dto.setTitle(survey.getTitle());
		        dto.setSurveyId(survey.getSurveyId() );
		        
		        // Sort sections in each SurveyVersion
		        for (SurveyVersion version : survey.getVersions()) {
		        	
		        	if(version.getSurveyVersionId() == survey.getActiveVersion()) {
		        		List<SurveyVersion> temp = new ArrayList<>();
		        		temp.add(version);
		        		dto.setVersions(temp);
		        		break;
		        	}
		        	
		            // Get the sections list
		            List<Section> sections = version.getSections();
		            
		            // Sort the sections by orderIndex
		            sections.sort(Comparator.comparingLong(Section::getOrderIndex));
		        }
		        
		        
		        return dto;
		    } else {
		        return null;
		    }
			
	}


	@Override
	public List<Section> viewSurveySecion(Long surveyVersionId) {
		// TODO Auto-generated method stub
		
		SurveyVersion version = surveyVersionRepo.findById(surveyVersionId).get();
		List<Section> res = version.getSections();
		return res;
	}


	@Override
	public void changeStatus(Long versionId) {
		SurveyVersion version = surveyVersionRepo.findById(versionId).get();
		version.setLive(!version.isLive());
	}

	@Override
	public void modifySectionOrder(List<Section> list, Long versionId) {
	    SurveyVersion version = surveyVersionRepo.findById(versionId)
	            .orElseThrow(() -> new EntityNotFoundException("SurveyVersion with id " + versionId + " not found"));

	    // Fetch existing sections for this version
	    List<Section> existingSections = version.getSections();

	    // Iterate over the input list and update existing sections
	    long index = 1;
	    for (Section inputSection : list) {
	        // Find the corresponding existing section by id
	        Section existingSection = findExistingSection(existingSections, inputSection.getSectionId());

	        if (existingSection != null) {
	            // Update existing section properties
	            existingSection.setOrderIndex(index++);
	            existingSection.setSurveyVersion(version);

	            // Update SectionOptions if needed
	            for (SectionOption so : inputSection.getSectionOptions()) {
	                so.setSection(existingSection);
	            }

	            // Save the updated existing section
	            sectionRepo.save(existingSection);
	        }
	    }
	}

	// Helper method to find existing section by id
	private Section findExistingSection(List<Section> existingSections, Long sectionId) {
	    for (Section section : existingSections) {
	        if (section.getSectionId().equals(sectionId)) {
	            return section;
	        }
	    }
	    return null;
	}

	@Override
	public void modifyVersionStatus(Long versionId) {
		
		SurveyVersion version = surveyVersionRepo.findById(versionId).get();
		version.setLive(!version.isLive());
//		System.out.println(version.getSurveyVersionId());
		Long surveyId = surveyVersionRepo.findSurveyIdByVersion(versionId);
		Survey survey = surveyRepo.getById(surveyId);
		survey.setActiveVersion(versionId);
	
	}



}
