package com.oracle.survey_creator_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class SectionOption {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long SectionOptionId;
    
    private String optionTitle;
    
    @ManyToOne()
    @JoinColumn(name = "section_id")
    private Section section;
    
    public SectionOption() {
		// TODO Auto-generated constructor stub
	}


    



	public void setSection(Section section) {
		this.section = section;
	}



	public Long getSectionOptionId() {
		return SectionOptionId;
	}






	public String getOptionTitle() {
		return optionTitle;
	}






	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}
	
	



    

    
    
    // Constructor, getters, and setters (for brevity)
}
