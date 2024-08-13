package com.oracle.survey_creator_service.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.oracle.survey_creator_service.entity.SurveyVersion;


public class SurveyDto {
	public Long surveyId;
    
	public String title;
	public String description;
	public String owner;
	public LocalDateTime createdOn;
    
	public Long activeVersion;
    public List<SurveyVersion> versions;
	public Long getSurveyId() {
		return surveyId;
	}
	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public Long getActiveVersion() {
		return activeVersion;
	}
	public void setActiveVersion(Long activeVersion) {
		this.activeVersion = activeVersion;
	}
	public List<SurveyVersion> getVersions() {
		return versions;
	}
	public void setVersions(List<SurveyVersion> versions) {
		this.versions = versions;
	}
    
    
}
