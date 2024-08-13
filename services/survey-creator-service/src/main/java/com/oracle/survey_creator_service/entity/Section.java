package com.oracle.survey_creator_service.entity;

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
public class Section {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sectionId;
    
    private Long orderIndex;
    private String sectionType;
    private String sectionTitle;
    
    @ManyToOne()
    @JoinColumn(name = "survey_version_id")
    private SurveyVersion surveyVersion;
    
    public Section() {
		// TODO Auto-generated constructor stub
	}
    
    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<SectionOption> sectionOptions;

	public Section(Long orderIndex, String sectionType, SurveyVersion surveyVersion,
			List<SectionOption> sectionOptions) {
		super();
		this.orderIndex = orderIndex;
		this.sectionType = sectionType;
		this.surveyVersion = surveyVersion;
		this.sectionOptions = sectionOptions;
	}
	

	public Long getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Long orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getSectionType() {
		return sectionType;
	}

	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}


	public List<SectionOption> getSectionOptions() {
		return sectionOptions;
	}

	public void setSectionOptions(List<SectionOption> sectionOptions) {
		this.sectionOptions = sectionOptions;
	}



	public void setSurveyVersion(SurveyVersion surveyVersion) {
		this.surveyVersion = surveyVersion;
	}


	public Long getSectionId() {
		return sectionId;
	}


	public String getSectionTitle() {
		return sectionTitle;
	}


	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}


	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	
	

	


	
	
    
    // Constructor, getters, and setters (for brevity)
    
    
}
