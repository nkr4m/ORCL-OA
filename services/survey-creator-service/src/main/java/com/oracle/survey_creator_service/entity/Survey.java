package com.oracle.survey_creator_service.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Survey {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyId;
    
    private String title;
    private String description;
    private String owner;
    private LocalDateTime createdOn;
    
    private Long activeVersion;
    
    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<SurveyVersion> versions;
    
    // Constructor, getters, and setters (for brevity)
    
    public Survey() {
		// TODO Auto-generated constructor stub
	}

	public Survey(String title, String owner, LocalDateTime createdOn, List<SurveyVersion> versions) {
		super();
		this.title = title;
		this.owner = owner;
		this.createdOn = createdOn;
		this.versions = versions;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public List<SurveyVersion> getVersions() {
		return versions;
	}

	public void setVersions(List<SurveyVersion> versions) {
		this.versions = versions;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getActiveVersion() {
		return activeVersion;
	}

	public void setActiveVersion(Long activeVersion) {
		this.activeVersion = activeVersion;
	}
	
	
	
	


	
    
    
}
