package com.oracle.survey_creator_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oracle.survey_creator_service.entity.SectionOption;

public interface SectionOptionRepository extends JpaRepository<SectionOption, Long> {

}
