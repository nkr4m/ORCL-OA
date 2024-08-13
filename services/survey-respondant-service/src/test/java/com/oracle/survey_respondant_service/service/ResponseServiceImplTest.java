package com.oracle.survey_respondant_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.oracle.survey_respondant_service.dto.ResponseDto;
import com.oracle.survey_respondant_service.entity.Response;
import com.oracle.survey_respondant_service.exception.RespondantServiceException;
import com.oracle.survey_respondant_service.repository.ResponseRepository;

public class ResponseServiceImplTest {

    @Mock
    private ResponseRepository responseRepository;

    @InjectMocks
    private ResponseServiceImpl responseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveResponse_SUCCESS() throws RespondantServiceException {
        // Create sample data
        List<ResponseDto> responses = new ArrayList<>();
        responses.add(new ResponseDto());
        responses.add(new ResponseDto());

        Optional<Long> id = Optional.empty();
        Long surveyId = 1L;
        Long versionId = 1L;

        String generatedUUID = "generated-uuid";
        
        ResponseServiceImpl spyResponseService = spy(responseService);
        doReturn(generatedUUID).when(spyResponseService).uUIDString();

        when(responseRepository.saveAndFlush(any(Response.class)))
                .thenAnswer(invocation -> {
                    Response savedResponse = invocation.getArgument(0);
                    // Assert the savedResponse properties here if needed
                    assertEquals(generatedUUID, savedResponse.getUserId());
                    assertEquals(surveyId, savedResponse.getSurveyId());
                    assertEquals(versionId, savedResponse.getVersionId());
                    return savedResponse;
                });

        spyResponseService.saveResponse(responses, id, surveyId, versionId);

        verify(responseRepository, times(2)).saveAndFlush(any(Response.class));
    }
    
    
    @Test
    void saveResponse_FAIL() throws RespondantServiceException {
    	List<ResponseDto> responses = new ArrayList<>();
        responses.add(new ResponseDto());
        responses.add(new ResponseDto());

        Optional<Long> id = Optional.empty();
        Long surveyId = null;
        Long versionId = 1L;

        String generatedUUID = "generated-uuid";
        ResponseServiceImpl spyResponseService = spy(responseService);
        doReturn(generatedUUID).when(spyResponseService).uUIDString();

        assertThrows(RespondantServiceException.class, () -> responseService.saveResponse(responses, id, surveyId, versionId));
     

    }




}
