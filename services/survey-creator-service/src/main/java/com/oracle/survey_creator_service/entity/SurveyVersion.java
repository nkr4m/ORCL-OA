package com.oracle.survey_creator_service.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class SurveyVersion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long SurveyVersionId;
    
    private String versionTitle;
    private String description;
    private LocalDateTime createdOn;
    private boolean isLive;
    private boolean responded;
    
    @ManyToOne()
    @JoinColumn(name = "survey_id")
    private Survey survey;
    
    @OneToMany(mappedBy = "surveyVersion", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Section> sections;


	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}


	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Long getSurveyVersionId() {
		return SurveyVersionId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isResponded() {
		return responded;
	}

	public void setResponded(boolean responded) {
		this.responded = responded;
	}

	public String getVersionTitle() {
		return versionTitle;
	}

	public void setVersionTitle(String versionTitle) {
		this.versionTitle = versionTitle;
	}

	public void setSurveyVersionId(Long surveyVersionId) {
		SurveyVersionId = surveyVersionId;
	}
	

	
	
	
	


    // Constructor, getters, and setters (for brevity)
}
