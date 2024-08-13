package com.oracle.survey_creator_service.dto;

import java.util.List;

public class SurveySectionDto {
	
	public String sectionTitle;
	public String sectionType;
	public List<SurveySectionOptionDto> sectionOptions;
	public String getSectionTitle() {
		return sectionTitle;
	}
	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}
	public String getSectionType() {
		return sectionType;
	}
	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}
	public List<SurveySectionOptionDto> getSectionOptions() {
		return sectionOptions;
	}
	public void setSectionOptions(List<SurveySectionOptionDto> sectionOptions) {
		this.sectionOptions = sectionOptions;
	}
	
	
	
	

}
