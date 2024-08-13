package com.oracle.survey_respondant_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.oracle.survey_respondant_service.entity.Response;

public interface ResponseRepository extends JpaRepository<Response, Long> {
	Response findByUserId(String userId);
	
	List<Response> findByVersionId(Long versionId);
}
